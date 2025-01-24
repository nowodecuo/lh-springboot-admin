/***
 * 工具类
 * @author 1874
 */
package luohao.application.common.utils;
import com.antherd.smcrypto.sm4.Sm4;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.enums.ResEnum;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 表单数据校验
     * @param data 验证数据
     */
    public static void formCheck(FormValidator data) throws Exception {
        Object value = data.getValue(); // 值
        String pattern = data.getPattern(); // 正则
        Function<String, String> function = data.getFunction(); // 自定义函数
        // 自定义函数校验
        if (function != null) {
            // 执行自定义函数，返回校验结果
            String res = function.apply((String) value);
            // 不等于SUCCESS则返回报错信息
            if (!res.equals(ResEnum.SUCCESS.VALUE)) {
                throw new Exception(res);
            }
        // 其他校验
        } else {
            // 默认为空校验
            if (value == null || value.toString().trim().equals("")) {
                throw new Exception(data.getMessage());
            }
            // 如果正则字符不为null && 正则字符不为"" && 正则匹配不通过，则返回报错信息
            if (pattern != null && !pattern.equals("") && !Pattern.matches(pattern, (String) value)) {
                throw new Exception(data.getMessage());
            }
        }
    }
    /**
     * 生成jwt令牌
     * @param data 自定义存储内容
     * @param secret 秘钥
     */
    public static String createJwt(Map<String, Object> data, String secret, Integer expireTime) {
        Date expTime = new Date(System.currentTimeMillis() + (expireTime * 1000)); // 过期时间/ms
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secret) // 签名算法
                .setClaims(data) // 自定义内容
                .setExpiration(expTime) // 过期时间
                .compact();
    }
    /**
     * 解析jwt令牌
     * @param jwt 令牌
     * @param secret 秘钥
     */
    public static Map<String, Object> decryptJwt(String jwt, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
    }
    /**
     * MD5 + SHA-256加密
     * @param content 需要加密的内容
     */
    public static String encryption(String content) {
        try {
            String md5 = DigestUtils.md5DigestAsHex(content.getBytes()); // md5加密
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); // sha-256加密
            byte[] bytes = messageDigest.digest(md5.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
    /**
     * SM4 加密
     */
    public static String sm4Encryption(String key, String content) throws Exception {
        try {
            return Sm4.encrypt(content, key);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /**
     * SM4 解密
     */
    public static String sm4Decryption(String key, String enContent) throws Exception {
        try {
            return Sm4.decrypt(enContent, key);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /**
     * 文件上传
     * @param file 上传的文件信息
     * @param uploadPath 上传目录路径
     */
    public static String fileUpload(MultipartFile file, String uploadPath) throws IOException {
        if (file.isEmpty()) throw new IOException("请上传文件");
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 文件生成UUID随机名
        String newFileName = createUUIDName(fileName);
        // 移动的完整文件路径
        File fileDir = new File(uploadPath + "/" + newFileName);
        // 如果目录不存在，则创建它
        if (!fileDir.getParentFile().exists()) {
            fileDir.getParentFile().mkdirs();
        }
        // 保存文件到指定目录
        file.transferTo(fileDir);
        return newFileName;
    }
    /**
     * 文件下载
     * @param filePath 下载的文件路径
     */
    public static ResponseEntity<InputStreamResource> fileDownload(String filePath) throws IOException {
        // 指定下载的文件
        File file = new File(filePath);
        // 文件是否存在
        if (!file.exists()) return ResponseEntity.notFound().build();
        // 创建一个InputStreamResource，它封装了文件输入流
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file.toPath()));
        // 设置HTTP响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        // 返回包含文件内容的InputStreamResource作为响应体
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    /**
     * 文件夹删除
     */
    public static void deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFolder(file.getPath());
                    } else {
                        file.delete();
                    }
                }
            }
            folder.delete();
        }
    }
    /**
     * 文件名称生成UUID随机名
     * @param fileName 文件名称
     */
    public static String createUUIDName(String fileName) {
        int index = fileName.indexOf(".");
        String extName = fileName.substring(index);
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 去掉-
        return uuid + extName;
    }
    /**
     * 父id拼接
     */
    public static String pidConcatData(String pids, String pid) {
        String[] parentPids = pids.split(","); // 转为数组
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(parentPids)); // 创建arrayList
        arrayList.add(pid); // 加入数组
        return String.join(",", arrayList); // 转为逗号分隔字符
    }
}
