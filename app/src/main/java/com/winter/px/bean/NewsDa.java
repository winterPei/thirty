package com.winter.px.bean;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/6.
 */
public class NewsDa {

    /**
     * title : 双盘合璧 “芝”你所想 东芝购X300送Q300
     * pic_url : http://img2.fengniao.com/article/12_280x210/963/liGnyaQ2RnETQ.jpg
     * doc_url : http://api.fengniao.com/app_ipad/news_doc.php?docid=5338625&isPad=1
     * comment_page_num : 0
     * comments_num : 0
     * more_comment_url : http://api.fengniao.com/app_ipad/news_doc_comments.php?docid=5338625&isPad=1
     * doc_id : 5338625
     * web_url : http://qicai.fengniao.com/533/5338625.html
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String title;
        private String pic_url;
        private String web_url;

        @Override
        public String toString() {
            return "ListBean{" +
                    "title='" + title + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", web_url='" + web_url + '\'' +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
