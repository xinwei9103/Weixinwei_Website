package xinwei.web.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xinwei on 1/27/2017.
 */
@DynamoDBTable(tableName = "news")
public class News {

    private String email;
    private long timeStamp;
    private int type;
    private NewsContent newsContent;
    private Date date;

    public News() {
        newsContent = new NewsContent();
        Date current = new Date();
        date = current;
        timeStamp = current.getTime() / 1000;
        type = 1;
    }

    @DynamoDBIgnore
    public Date getDate() {
        return date;
    }

    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBRangeKey(attributeName = "date")
    @DynamoDBIndexRangeKey(attributeName = "date", globalSecondaryIndexName = "typeIndex")
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        date = new Date(timeStamp * 1000);
    }

    @DynamoDBIndexHashKey(attributeName = "type", globalSecondaryIndexName = "typeIndex")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "newsContent")
    public NewsContent getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(NewsContent newsContent) {
        this.newsContent = newsContent;
    }

    public void setMessage(String message) {
        newsContent.setMessage(message);
    }

    public void addImage(String imageAddress) {
        newsContent.addImage(imageAddress);
    }


    @DynamoDBDocument
    public static class NewsContent {

        private String message;

        private List<String> images;

        public NewsContent() {
            images = new ArrayList<String>();
        }

        @DynamoDBAttribute(attributeName = "message")
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @DynamoDBAttribute(attributeName = "images")
        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public void addImage(String imageAddress) {
            if (images.size() >= 6) {
                return;
            }
            images.add(imageAddress);
        }
    }

}
