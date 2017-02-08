package xinwei.web.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xinwei.web.bean.User;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by xinwei on 2/1/2017.
 */
@Component
public class FileUploadUtil {

    private AmazonS3 s3client;

    public AmazonS3 getS3client() {
        return s3client;
    }
    @Resource(name = "amazonS3")
    public void setS3client(AmazonS3 s3client) {
        this.s3client = s3client;
    }


    public void upload(MultipartFile file, String fileName,String key) throws IOException, ImageProcessingException, MetadataException {
        //File temp =null;
        try {
            //temp= ImageUtil.rotateImage(file,"tempFile/"+fileName);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            PutObjectRequest request = new PutObjectRequest("weixinwei", key, file.getInputStream(),objectMetadata);
            AccessControlList acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
            request.setAccessControlList(acl);
            s3client.putObject(request);
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            if(temp!=null){
                temp.delete();
            }
            */
        }
    }

}
