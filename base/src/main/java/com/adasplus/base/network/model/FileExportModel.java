package com.adasplus.base.network.model;

/**
 * Author:刘净辉
 * Date : 2019/10/14 10:41
 * Description :
 */
public class FileExportModel {


    /**
     * StatuesCode : 0
     * Result : {"fileOutNumber":1,"fileOutIndex":0,"fileOutProgress":50}
     */

    private int StatuesCode;
    private ResultBean Result;

    @Override
    public String toString() {
        return "FileExportModel{" +
                "StatuesCode=" + StatuesCode +
                ", Result=" + Result +
                '}';
    }

    public int getStatuesCode() {
        return StatuesCode;
    }

    public void setStatuesCode(int StatuesCode) {
        this.StatuesCode = StatuesCode;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * fileOutNumber : 1
         * fileOutIndex : 0
         * fileOutProgress : 50
         */

        private int fileOutNumber;
        private int fileOutIndex;
        private int fileOutProgress;

        public int getFileOutNumber() {
            return fileOutNumber;
        }

        public void setFileOutNumber(int fileOutNumber) {
            this.fileOutNumber = fileOutNumber;
        }

        public int getFileOutIndex() {
            return fileOutIndex;
        }

        public void setFileOutIndex(int fileOutIndex) {
            this.fileOutIndex = fileOutIndex;
        }

        public int getFileOutProgress() {
            return fileOutProgress;
        }

        public void setFileOutProgress(int fileOutProgress) {
            this.fileOutProgress = fileOutProgress;
        }
    }
}
