package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.clsb.bean;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/18 15:39
 * 修改人：Administrator
 * 修改时间：2017/12/18 15:39
 * 修改备注：
 */

public class FileBean {

    private String filePath;//文件在本地的路径
    private String fileName;//本地文件名
    private String returnName;//上传完毕，系统返回的文件名
    private float progress;//进度条进度值
    private long currentSize;//当前上传了多少
    private long totalSize;//文件总的大小
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

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public float getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
