package com.adasplus.homepager.activate.mvp.model;

import java.util.List;

/**
 * Created by dell on 2019/11/25 14:55
 * Description:
 * Emain: 1187278976@qq.com
 */
public class LandmarkTypeModel {

    private List<LandmarkBean> landmark;

    public List<LandmarkBean> getLandmark() {
        return landmark;
    }

    public void setLandmark(List<LandmarkBean> landmark) {
        this.landmark = landmark;
    }

    public static class LandmarkBean {
        /**
         * landmark_type : 保留
         * landmark_code : 0
         */

        private String landmark_type;
        private String landmark_code;

        public String getLandmark_type() {
            return landmark_type;
        }

        public void setLandmark_type(String landmark_type) {
            this.landmark_type = landmark_type;
        }

        public String getLandmark_code() {
            return landmark_code;
        }

        public void setLandmark_code(String landmark_code) {
            this.landmark_code = landmark_code;
        }
    }
}
