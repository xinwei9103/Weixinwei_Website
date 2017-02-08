package xinwei.web.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

/**
 * Created by xinwei on 1/26/2017.
 */
@DynamoDBTable(tableName = "user")
public class User {
    private String userEmail;
    private String password;
    private long timeStamp;
    private Date date;
    private String imageURL;
    private String displayName;

    public User() {
    }

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
        this.imageURL = "https://s3-us-west-2.amazonaws.com/weixinwei/default/default.png";
        //this.date = new Date();
        this.displayName = userEmail;
        this.timeStamp = new Date().getTime() / 1000;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @DynamoDBAttribute(attributeName = "image")
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @DynamoDBIgnore
    public Date getDate() {

        return new Date(timeStamp * 1000);

    }

    @DynamoDBAttribute(attributeName = "date")
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @DynamoDBHashKey(attributeName = "email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
