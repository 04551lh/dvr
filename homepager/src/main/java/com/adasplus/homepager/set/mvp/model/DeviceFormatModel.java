package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.IDeviceFormatContract;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/12 14:33
 * Description :
 */
public class DeviceFormatModel implements IDeviceFormatContract.Model {

    private List<ArrayBean> Array;

    public List<ArrayBean> getArray() {
        return Array;
    }

    public void setArray(List<ArrayBean> Array) {
        this.Array = Array;
    }

    @Override
    public String toString() {
        return "DeviceFormatModel{" +
                "Array=" + Array +
                '}';
    }

    public static class ArrayBean {

        private String storageName;
        private int capacity;
        private int used;
        private int free;
        private int selectEnable;

        public int getSelectEnable() {
            return selectEnable;
        }

        public void setSelectEnable(int selectEnable) {
            this.selectEnable = selectEnable;
        }

        @Override
        public String toString() {
            return "ArrayBean{" +
                    "storageName='" + storageName + '\'' +
                    ", capacity=" + capacity +
                    ", used=" + used +
                    ", free=" + free +
                    ", selectEnable=" + selectEnable +
                    '}';
        }

        public String getStorageName() {
            return storageName;
        }

        public void setStorageName(String storageName) {
            this.storageName = storageName;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getUsed() {
            return used;
        }

        public void setUsed(int used) {
            this.used = used;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }
    }
}
