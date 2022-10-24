package top.xc27.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xc27.common.MinioUtil;
import top.xc27.common.R;

@RestController
@RequestMapping("common")
public class FileController {

    @Autowired
    MinioUtil minioUtil;

    @PostMapping("upload")
    public R<String> upload(MultipartFile file){
        String upload = null;
        try {
            upload = minioUtil.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("upload = " + upload);
        return R.success(upload);
    }

    @GetMapping("download")
    public void preview(String name) {
//        String decodeStr = Base64.decodeStr(fileName);
        minioUtil.preview(name);
    }

//    @GetMapping("/{fileName}")
//    public R<String> preview(@PathVariable("fileName") String fileName) {
//        String url = "";
//        try {
//            url = minioUtil.pictureUrl(fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return R.success(url);
//    }
}
