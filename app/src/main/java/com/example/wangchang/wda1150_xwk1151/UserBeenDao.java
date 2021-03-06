package com.example.wangchang.wda1150_xwk1151;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.wangchang.wda1150_xwk1151.Been.UserBeen;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_BEEN".
*/
public class UserBeenDao extends AbstractDao<UserBeen, Long> {

    public static final String TABLENAME = "USER_BEEN";

    /**
     * Properties of entity UserBeen.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Email = new Property(1, String.class, "email", false, "EMAIL");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
    }


    public UserBeenDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeenDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_BEEN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"EMAIL\" TEXT NOT NULL ," + // 1: email
                "\"PASSWORD\" TEXT NOT NULL );"); // 2: password
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_BEEN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserBeen entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getEmail());
        stmt.bindString(3, entity.getPassword());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserBeen entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getEmail());
        stmt.bindString(3, entity.getPassword());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserBeen readEntity(Cursor cursor, int offset) {
        UserBeen entity = new UserBeen( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // email
            cursor.getString(offset + 2) // password
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserBeen entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEmail(cursor.getString(offset + 1));
        entity.setPassword(cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserBeen entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserBeen entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserBeen entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
