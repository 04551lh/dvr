package com.adasplus.homepager.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author:刘净辉
 * Date : 2019/10/23 11:51
 * Description :
 */
public class VehicleInfoUtil {

    private final static String PlateFileColor = "license_plate_color.json";
    private final static String RegionCodeFileName = "administrative_region_code.json";

    public static String readVehicleData(Context context,String fileName){
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            String standardCharsets = "UTF-8";
            BufferedReader br = new BufferedReader(new InputStreamReader(is, standardCharsets));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String readPlateFileColor(Context context){
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(PlateFileColor);
            String standardCharsets = "UTF-8";
            BufferedReader br = new BufferedReader(new InputStreamReader(is, standardCharsets));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String readRegionCodeFileName(Context context){
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(RegionCodeFileName);
            String standardCharsets = "UTF-8";
            BufferedReader br = new BufferedReader(new InputStreamReader(is, standardCharsets));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
