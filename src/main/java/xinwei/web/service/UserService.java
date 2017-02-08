package xinwei.web.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xinwei.web.bean.User;
import xinwei.web.util.FileUploadUtil;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by xinwei on 1/26/2017.
 */
@Component
public class UserService {

    private DynamoDBMapper dynamoDBMapper;

    private FileUploadUtil fileUploadUtil;

    public FileUploadUtil getFileUploadUtil() {
        return fileUploadUtil;
    }
    @Resource
    public void setFileUploadUtil(FileUploadUtil fileUploadUtil) {
        this.fileUploadUtil = fileUploadUtil;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }


    @Resource
    public void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public User getUser(String userEmail) {
        //System.out.println(userEmail+"user service");
        User user = dynamoDBMapper.load(User.class, userEmail, null);
        //System.out.println(user.getUserEmail());
        //System.out.println(user.getDate());
        return user;
    }

    public User checkUser(String userEmail, String password) {
        //System.out.println(userEmail);
        User user = getUser(userEmail);
        if (user == null) {
            return null;
        }
        //System.out.println(user.getUserEmail());
        if (password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean checkEmail(String userEmail) {
        //System.out.println(userEmail);
        User user = getUser(userEmail);
        if (user == null) {
            return false;
        }
        return true;
    }

    public User checkUser(User user) {
        return checkUser(user.getUserEmail(), user.getPassword());
    }

    public void updateUser(User user) {
        dynamoDBMapper.save(user);
    }

    public void updateUser(User user, MultipartFile file) {

        //String[] iconNamePart = file.getOriginalFilename().split("\\.");
        //System.out.println(iconNamePart[1]);
        String fileName = user.getUserEmail() + "_icon.jpg";
        String key = user.getUserEmail() + "/icon.jpg";
        try {
            fileUploadUtil.upload(file,fileName,key);
            user.setImageURL("https://s3-us-west-2.amazonaws.com/weixinwei/" + key);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        }
        dynamoDBMapper.save(user);
    }
    public boolean saveUser(User user) {
        if (!checkEmail(user.getUserEmail())) {
            updateUser(user);
            return true;
        }
        return false;
    }

    public boolean saveUser(User user, MultipartFile file) {
        if (!checkEmail(user.getUserEmail())) {
            updateUser(user,file);
            return true;
        }
        return false;
    }

}
