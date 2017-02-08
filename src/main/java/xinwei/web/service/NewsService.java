package xinwei.web.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xinwei.web.bean.News;
import xinwei.web.bean.User;
import xinwei.web.util.FileUploadUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei on 1/27/2017.
 */
@Component
public class NewsService {

    private AmazonS3 s3client;

    private DynamoDBMapper dynamoDBMapper;
    private FileUploadUtil fileUploadUtil;

    public FileUploadUtil getFileUploadUtil() {
        return fileUploadUtil;
    }

    @Resource
    public void setFileUploadUtil(FileUploadUtil fileUploadUtil) {
        this.fileUploadUtil = fileUploadUtil;
    }

    public AmazonS3 getS3client() {
        return s3client;
    }

    @Resource(name = "amazonS3")
    public void setS3client(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    @Resource
    public void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<MultipartFile> valFiles(List<MultipartFile> files) {

        List<MultipartFile> list = new ArrayList<MultipartFile>();
        for (MultipartFile file : files) {
            if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
                continue;
            }
            list.add(file);
        }
        return list;
    }

    public void createNews(User user, String message, List<MultipartFile> files) {

        List<MultipartFile> images = valFiles(files);
        if ((message == null || message.length() == 0) && images.isEmpty()) {
            return;
        }

        String userEmail = user.getUserEmail();
        News news = new News();
        news.setEmail(userEmail);
        if (message == null) {
            news.setMessage("");
        } else {
            news.setMessage(message);
        }
        int i = 1;
        for (MultipartFile file : images) {
            if(i>6){
                break;
            }
            //String[] fileNamePart = file.getOriginalFilename().split("\\.");
            //System.out.println(iconNamePart[1]);
            String key = userEmail + "/" + news.getTimeStamp() + "/" + i + ".jpg";
            String fileName = userEmail + "_" + news.getTimeStamp() + "_" + i + ".jpg";
            try {
                fileUploadUtil.upload(file, fileName, key);
                news.addImage("https://s3-us-west-2.amazonaws.com/weixinwei/" + key);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ImageProcessingException e) {
                e.printStackTrace();
            } catch (MetadataException e) {
                e.printStackTrace();
            }
            i++;
        }
        dynamoDBMapper.save(news);
    }

    public QueryResultPage<News> getFirstPage() {
        News hashkey = new News();
        hashkey.setType(1);
        DynamoDBQueryExpression<News> queryExpression = new DynamoDBQueryExpression<News>()
                .withIndexName("typeIndex")
                .withHashKeyValues(hashkey)
                .withConsistentRead(false)
                .withLimit(6)
                .withScanIndexForward(false);
        QueryResultPage<News> newes = dynamoDBMapper.queryPage(News.class, queryExpression);
        return newes;
    }

    public QueryResultPage<News> getNextPage(Map<String, AttributeValue> key) {
        if (key == null) {
            return getFirstPage();
        }
        News hashkey = new News();
        hashkey.setType(1);
        DynamoDBQueryExpression<News> queryExpression = new DynamoDBQueryExpression<News>()
                .withIndexName("typeIndex")
                .withHashKeyValues(hashkey)
                .withConsistentRead(false)
                .withLimit(6)
                .withScanIndexForward(false)
                .withExclusiveStartKey(key);
        QueryResultPage<News> newes = dynamoDBMapper.queryPage(News.class, queryExpression);
        return newes;
    }

    public QueryResultPage<News> getPrePage(Map<String, AttributeValue> key) {
        if (key == null) {
            return getFirstPage();
        }
        News hashkey = new News();
        hashkey.setType(1);
        DynamoDBQueryExpression<News> queryExpression = new DynamoDBQueryExpression<News>()
                .withIndexName("typeIndex")
                .withHashKeyValues(hashkey)
                .withConsistentRead(false)
                .withLimit(1)
                .withScanIndexForward(false)
                .withExclusiveStartKey(key);
        QueryResultPage<News> newesKey = dynamoDBMapper.queryPage(News.class, queryExpression);
        //System.out.println(newesKey.getResults().get(0).getNewsContent().getMessage());
        DynamoDBQueryExpression<News> queryExpression2 = new DynamoDBQueryExpression<News>()
                .withIndexName("typeIndex")
                .withHashKeyValues(hashkey)
                .withConsistentRead(false)
                .withLimit(7)
                .withExclusiveStartKey(newesKey.getLastEvaluatedKey());
        QueryResultPage<News> newes = dynamoDBMapper.queryPage(News.class, queryExpression2);
        if (newes.getCount() < 7) {
            return null;
        }
        return newes;
    }


    public void deleteNews(String email, long time) {
        News news = new News();
        news.setEmail(email);
        news.setTimeStamp(time);
        dynamoDBMapper.delete(news);

        ListObjectsV2Result result = s3client.listObjectsV2("weixinwei", email + "/" + time);
        if (!result.getObjectSummaries().isEmpty()) {
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest("weixinwei");
            List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();

            for (S3ObjectSummary s3ObjectSummary : result.getObjectSummaries()) {
                keys.add(new DeleteObjectsRequest.KeyVersion(s3ObjectSummary.getKey()));
            }
            multiObjectDeleteRequest.setKeys(keys);
            DeleteObjectsResult delObjRes = s3client.deleteObjects(multiObjectDeleteRequest);
            //System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
        }

    }


}
