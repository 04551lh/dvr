package com.adasplus.activate.mvp.model;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/16 12:11
 * Description :
 */
public class AdministrativeRegionCodeModel {


    private String name;
    private String code;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AdministrativeRegionCodeModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", city=" + city +
                '}';
    }

    public static class CityBean {

        private String name;
        private String code;
        private List<AreaBean> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        @Override
        public String toString() {
            return "CityBean{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", area=" + area +
                    '}';
        }

        public static class AreaBean {

            private String name;
            private String code;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            @Override
            public String toString() {
                return "AreaBean{" +
                        "name='" + name + '\'' +
                        ", code='" + code + '\'' +
                        '}';
            }
        }
    }
}
