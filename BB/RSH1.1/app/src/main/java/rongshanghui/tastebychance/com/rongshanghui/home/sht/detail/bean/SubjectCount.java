package rongshanghui.tastebychance.com.rongshanghui.home.sht.detail.bean;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/27 19:55
 * 修改人：Administrator
 * 修改时间：2017/11/27 19:55
 * 修改备注：subjectCount
 */

public class SubjectCount {

    private int subject_type;
    private int id;
    private int member_count;
    private int fans_count;
    private int hits_count;
    private String image;
    private int shx;
    private int rzxm;
    private int zx;
    private int zc;
    private int fdxx;
    private int zsx;
    private String intro;
    private int is_cared;//1 已关注 0 未关注
    private String mobile;//  	电话
    private String area_code;//  	区号
    private int apply_status;// 	-1:一点关系都没有 0:待审核,1:审核通过,3.审核不通过,(只有商会才有该字段)
    private String reason;//  	拒绝入会理由(只有商会才有该字段)
    private String creditcard;// 	信用卡链接地址(机构才有这个字段)
    private String personalcredit;//  	个人信用贷链接地址(机构才有这个字段)
    private String video;//视频播放链接

    public int getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getHits_count() {
        return hits_count;
    }

    public void setHits_count(int hits_count) {
        this.hits_count = hits_count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getShx() {
        return shx;
    }

    public void setShx(int shx) {
        this.shx = shx;
    }

    public int getRzxm() {
        return rzxm;
    }

    public void setRzxm(int rzxm) {
        this.rzxm = rzxm;
    }

    public int getZx() {
        return zx;
    }

    public void setZx(int zx) {
        this.zx = zx;
    }

    public int getZc() {
        return zc;
    }

    public void setZc(int zc) {
        this.zc = zc;
    }

    public int getFdxx() {
        return fdxx;
    }

    public void setFdxx(int fdxx) {
        this.fdxx = fdxx;
    }

    public int getZsx() {
        return zsx;
    }

    public void setZsx(int zsx) {
        this.zsx = zsx;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getIs_cared() {
        return is_cared;
    }

    public void setIs_cared(int is_cared) {
        this.is_cared = is_cared;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getApply_status() {
        return apply_status;
    }

    public void setApply_status(int apply_status) {
        this.apply_status = apply_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getPersonalcredit() {
        return personalcredit;
    }

    public void setPersonalcredit(String personalcredit) {
        this.personalcredit = personalcredit;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
