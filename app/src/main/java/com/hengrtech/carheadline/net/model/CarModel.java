package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/1 09:45
 */

public class CarModel {


    /**
     * masterId : 7
     * name : 丰田
     * logoUrl : http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_7_100.png
     * initial : F
     * saleStatus : 1
     */

    private List<HotlistBean> hotlist;
    /**
     * letter : A
     * masters : [{"masterId":12,"name":"奥克斯","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_12_100.png","initial":"A","saleStatus":-1},{"masterId":228,"name":"Artega","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_228_100.png","initial":"A","saleStatus":1},{"masterId":180,"name":"AC Schnitzer","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_180_100.png","initial":"A","saleStatus":1},{"masterId":92,"name":"阿尔法·罗密欧","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_92_100.png","initial":"A","saleStatus":1},{"masterId":97,"name":"阿斯顿·马丁","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_97_100.png","initial":"A","saleStatus":1},{"masterId":9,"name":"奥迪","logoUrl":"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_9_100.png","initial":"A","saleStatus":1}]
     */

    private List<MasterlistBean> masterlist;

    public List<HotlistBean> getHotlist() {
        return hotlist;
    }

    public void setHotlist(List<HotlistBean> hotlist) {
        this.hotlist = hotlist;
    }

    public List<MasterlistBean> getMasterlist() {
        return masterlist;
    }

    public void setMasterlist(List<MasterlistBean> masterlist) {
        this.masterlist = masterlist;
    }

    public static class HotlistBean {
        private int masterId;
        private String name;
        private String logoUrl;
        private String initial;
        private int saleStatus;

        public int getMasterId() {
            return masterId;
        }

        public void setMasterId(int masterId) {
            this.masterId = masterId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public int getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(int saleStatus) {
            this.saleStatus = saleStatus;
        }
    }

    public static class MasterlistBean {
        private String letter;
        /**
         * masterId : 12
         * name : 奥克斯
         * logoUrl : http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_12_100.png
         * initial : A
         * saleStatus : -1
         */

        private List<MastersBean> masters;

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public List<MastersBean> getMasters() {
            return masters;
        }

        public void setMasters(List<MastersBean> masters) {
            this.masters = masters;
        }

        public static class MastersBean {
            private int masterId;
            private String name;
            private String logoUrl;
            private String initial;
            private int saleStatus;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getMasterId() {
                return masterId;
            }

            public void setMasterId(int masterId) {
                this.masterId = masterId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
            }

            public String getInitial() {
                return initial;
            }

            public void setInitial(String initial) {
                this.initial = initial;
            }

            public int getSaleStatus() {
                return saleStatus;
            }

            public void setSaleStatus(int saleStatus) {
                this.saleStatus = saleStatus;
            }
        }
    }
}
