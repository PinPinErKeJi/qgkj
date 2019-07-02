package com.jeeplus.modules.ureport.provider;

import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.jeeplus.modules.ureport.ftp.FTPClientUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * FTP文件服务器 报表存储
 *
 * @author qiaolin
 * @version 2018年5月9日
 */

@Component
@ConfigurationProperties(prefix = "ureport.ftp.provider")
public class FTPProvider implements ReportProvider {

    private static final String NAME = "ftp-provider";

    public static String getNAME() {
        return NAME;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public FTPClientUtils getFtpUtils() {
        return ftpUtils;
    }

    public void setFtpUtils(FTPClientUtils ftpUtils) {
        this.ftpUtils = ftpUtils;
    }

    private String basePath = "ureport_file/";
    private String prefix = "ftp:";

    private boolean disabled;

    @Autowired
    private FTPClientUtils ftpUtils;

    @Override
    public InputStream loadReport(String file) {
        return ftpUtils.downloadFile(getCorrectName(file));
    }

    @Override
    public void deleteReport(String file) {
        ftpUtils.delete(getCorrectName(file));
    }

    @Override
    public List<ReportFile> getReportFiles() {
        List<FTPFile> fileList = ftpUtils.getFileList(basePath);
        List<ReportFile> reportFile = new ArrayList<>();
        for (FTPFile ftpFile : fileList) {
            Calendar timestamp = ftpFile.getTimestamp();
            reportFile.add(new ReportFile(ftpFile.getName(), timestamp.getTime()));
        }
        return reportFile;
    }

    @Override
    public void saveReport(String file, String content) {
        ftpUtils.uploadFile(getCorrectName(file), new ByteArrayInputStream(content.getBytes()));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean disabled() {
        return disabled;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    /**
     * 获取没有前缀的文件名并加上FTP下存放Ureport文件夹前缀
     *
     * @param name
     * @return
     */
    private String getCorrectName(String name) {
        if (name.startsWith(prefix)) {
            name = name.substring(prefix.length(), name.length());
        }
        return basePath + name;
    }

}
