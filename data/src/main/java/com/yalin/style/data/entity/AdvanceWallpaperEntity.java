package com.yalin.style.data.entity;

import android.database.Cursor;
import android.text.TextUtils;

import com.yalin.style.data.log.LogUtil;
import com.yalin.style.data.repository.datasource.provider.StyleContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinyalin
 * @since 2017/7/28.
 */

public class AdvanceWallpaperEntity {
    private static final String TAG = "AdvanceWallpaperEntity";

    public int id;
    public String wallpaperId;
    public String link;
    public String name;
    public String author;
    public String iconUrl;
    public String downloadUrl;
    public String providerName;

    public boolean lazyDownload;
    public boolean needAd;

    public String storePath;
    public String checkSum;

    public boolean isDefault = false;

    public boolean isSelected = false;

    public static List<AdvanceWallpaperEntity> readCursor(Cursor cursor) {
        List<AdvanceWallpaperEntity> validWallpapers = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            AdvanceWallpaperEntity wallpaperEntity = readEntityFromCursor(cursor);
            try {
                if (!wallpaperEntity.lazyDownload
                        && !new File(wallpaperEntity.storePath).exists()) {
                    throw new FileNotFoundException("Component not found.");
                }
                validWallpapers.add(wallpaperEntity);
            } catch (Exception e) {
                LogUtil.D(TAG, "File not found with wallpaper wallpaperId : "
                        + wallpaperEntity.wallpaperId);
            }
        }
        return validWallpapers;
    }

    public static AdvanceWallpaperEntity readEntityFromCursor(Cursor cursor) {
        AdvanceWallpaperEntity wallpaperEntity = new AdvanceWallpaperEntity();

        wallpaperEntity.id = cursor.getInt(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper._ID));
        wallpaperEntity.name = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_NAME));
        wallpaperEntity.wallpaperId = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_WALLPAPER_ID));
        wallpaperEntity.iconUrl = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_ICON_URL));
        wallpaperEntity.link = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_LINK));
        wallpaperEntity.author = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_AUTHOR));
        wallpaperEntity.downloadUrl = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_DOWNLOAD_URL));
        wallpaperEntity.checkSum = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_CHECKSUM));
        wallpaperEntity.storePath = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_STORE_PATH));
        wallpaperEntity.providerName = cursor.getString(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_PROVIDER_NAME));
        wallpaperEntity.isSelected = cursor.getInt(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_SELECTED)) == 1;
        wallpaperEntity.lazyDownload = cursor.getInt(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_LAZY_DOWNLOAD)) == 1;
        wallpaperEntity.needAd = cursor.getInt(cursor.getColumnIndex(
                StyleContract.AdvanceWallpaper.COLUMN_NAME_NEED_AD)) == 1;

        return wallpaperEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AdvanceWallpaperEntity) {
            if (TextUtils.equals(((AdvanceWallpaperEntity) obj).name, name)
                    && TextUtils.equals(((AdvanceWallpaperEntity) obj).checkSum, checkSum)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + checkSum.hashCode();
        return result;
    }
}
