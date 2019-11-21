package com.adasplus.base.network.model;

/**
 * Author:刘净辉
 * Date : 2019/10/14 10:41
 * Description :
 */
public class FileExportModel {

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
