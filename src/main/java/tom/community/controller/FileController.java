package tom.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tom.community.dto.FileDTO;
//import tom.community.provider.UCloudProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {

    /*@Autowired
    private UCloudProvider uCloudProvider;//*/

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(//HttpServletRequest request
    ){
        /*MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request; //
        MultipartFile file = multipartRequest.getFile("editormd-image-file");//
        try {
            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO=new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl("/images/timg.png");
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        FileDTO fileDTO=new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/timg.png");
        return fileDTO;
    }

}
