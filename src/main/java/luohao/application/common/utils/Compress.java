/***
 * 文件压缩
 * @author 1874
 */
package luohao.application.common.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Compress {
    /**
     * 文件夹压缩开始
     * @param folderPath 文件夹目录
     * @param zipFolderPath 压缩后文件名及目录
     */
    public void compressFolderRun(String folderPath, String zipFolderPath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(Paths.get(zipFolderPath)))) {
            BufferedOutputStream bufferOut = new BufferedOutputStream(zipOut);
            File source = new File(folderPath);
            compressFolder(source, source.getName(), zipOut, bufferOut);
        }
    }
    /** 文件夹压缩方法 **/
    private void compressFolder(File folder, String baseName, ZipOutputStream zipOut, BufferedOutputStream bufferOut) throws IOException {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(baseName + "/" + file.getName());
                zipOut.putNextEntry(zipEntry);
                try (BufferedInputStream bufferIn = new BufferedInputStream(Files.newInputStream(file.toPath()));) {
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = bufferIn.read(bytes)) >= 0) {
                        bufferOut.write(bytes, 0, length);
                    }
                }
                zipOut.flush();
                zipOut.closeEntry();
            } else if (file.isDirectory()) {
                compressFolder(file, baseName + "/" + file.getName(), zipOut, bufferOut);
            }
        }
    }
}
