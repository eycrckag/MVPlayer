package com.itheima.mvplayer.model;

public class HomeItemBean {


    /**
     * type : WEEK_MAIN_STAR
     * subType : PLAYLIST
     * id : 4584196
     * title : 甜美如糖 嫣然似玉——唐嫣
     * posterPic : http://img1.c.yinyuetai.com/others/mobile_front_page/161209/0/-M-2aa989ffe0947995b29444cd09362c92_0x0.jpg
     * thumbnailPic : http://img3.c.yinyuetai.com/video/playlist/161205/0/-M-9374607f7fa11d433efd988f32d2f159_220x220.jpg
     * videoSize : 0
     * hdVideoSize : 0
     * uhdVideoSize : 0
     * status : 0
     * clickUrl : http://mapi.yinyuetai.com/statistics/click.json?id=4975
     */

    private String type;
    private String subType;
    private int id;
    private String title;
    private String posterPic;
    private String thumbnailPic;
    private int videoSize;
    private int hdVideoSize;
    private int uhdVideoSize;
    private int status;
    private String clickUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPic() {
        return posterPic;
    }

    public void setPosterPic(String posterPic) {
        this.posterPic = posterPic;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public int getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(int videoSize) {
        this.videoSize = videoSize;
    }

    public int getHdVideoSize() {
        return hdVideoSize;
    }

    public void setHdVideoSize(int hdVideoSize) {
        this.hdVideoSize = hdVideoSize;
    }

    public int getUhdVideoSize() {
        return uhdVideoSize;
    }

    public void setUhdVideoSize(int uhdVideoSize) {
        this.uhdVideoSize = uhdVideoSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}
