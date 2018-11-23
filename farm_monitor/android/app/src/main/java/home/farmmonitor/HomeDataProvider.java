package home.farmmonitor;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class HomeDataProvider extends ContentProvider {
    static final String PROVIDER_NAME = "home.farmmonitor.provider";
    static final String URL = "content://" + PROVIDER_NAME;
    static final Uri STATUS_URI = Uri.parse("content://" + PROVIDER_NAME + "/status");
    static final Uri DEVICES_URI = Uri.parse("content://" + PROVIDER_NAME + "/devices");
    static final Uri ROOMS_URI = Uri.parse("content://" + PROVIDER_NAME + "/rooms");

    static final int STATUS = 1;
    static final int DEVICES = 2;
    static final int ROOMS = 3;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "status", STATUS);
        uriMatcher.addURI(PROVIDER_NAME, "devices", DEVICES);
        uriMatcher.addURI(PROVIDER_NAME, "rooms", ROOMS);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case CONNECTIONSTATUS:

                break;

            case STUDENT_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
