package rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.zhinan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RongShangHui
 * 类描述：指南附件
 * 创建人：Administrator
 * 创建时间： 2017/12/16 16:16
 * 修改人：Administrator
 * 修改时间：2017/12/16 16:16
 * 修改备注：
 */

public class ZNFJFileBeans {

    public static ZNFJFileBeans getInstance() {
        return instance;
    }

    private static ZNFJFileBeans instance = new ZNFJFileBeans();

    private ZNFJFileBeans() {

    }

    private List<FileBean> files = new ArrayList<FileBean>();

    public List<FileBean> getFiles() {
        return files;
    }

    public void setFiles(List<FileBean> files) {
        this.files = files;
    }

    public static class FileBean {
        private String filePath;//文件在本地的路径
        private String fileName;//本地文件名
        private String returnName;//上传完毕，系统返回的文件名
        private float progress;//进度条进度值
        private int status;//上传状态: -1：上传失败；0：正在上传；1：上传成功

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getReturnName() {
            return returnName;
        }

        public void setReturnName(String returnName) {
            this.returnName = returnName;
        }

        public float getProgress() {
            return progress;
        }

        public void setProgress(float progress) {
            this.progress = progress;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public void addItem(FileBean file) {
        if (null == files) {
            files = new ArrayList<>();
        }

        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFileName().equals(file.getFileName())) {
                removeItem(file.getFileName());
            }
            files.add(file);
        }

        if (files.size() == 0) {
            files.add(file);
        }
    }

    public void removeItem(String fileName) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFileName().equals(fileName)) {
                files.remove(i);
                i--;
            }
        }
    }
}
