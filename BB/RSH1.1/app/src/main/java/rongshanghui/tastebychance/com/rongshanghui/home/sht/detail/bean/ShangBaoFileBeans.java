package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RongShangHui
 * 类描述：部门、机构、镇街-上报附件
 * 创建人：Administrator
 * 创建时间： 2017/12/16 16:16
 * 修改人：Administrator
 * 修改时间：2017/12/16 16:16
 * 修改备注：
 */

public class ShangBaoFileBeans {

    public static ShangBaoFileBeans getInstance() {
        return instance;
    }

    private static ShangBaoFileBeans instance = new ShangBaoFileBeans();

    private ShangBaoFileBeans() {

    }

    private List<FileBean> files = new ArrayList<FileBean>();

    public List<FileBean> getFiles() {
        return files;
    }

    public void setFiles(List<FileBean> files) {
        this.files = files;
    }

    public static class FileBean {
        private String fileName;
        private String returnName;

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
