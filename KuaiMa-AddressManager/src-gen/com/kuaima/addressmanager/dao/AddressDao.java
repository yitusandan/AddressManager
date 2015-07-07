package com.kuaima.addressmanager.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.kuaima.addressmanager.dao.Address;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ADDRESS.
*/
public class AddressDao extends AbstractDao<Address, Long> {

    public static final String TABLENAME = "ADDRESS";

    /**
     * Properties of entity Address.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property MobileNo = new Property(2, String.class, "mobileNo", false, "MOBILE_NO");
        public final static Property Area = new Property(3, String.class, "area", false, "AREA");
        public final static Property Street = new Property(4, String.class, "street", false, "STREET");
        public final static Property Detail = new Property(5, String.class, "detail", false, "DETAIL");
        public final static Property IsDefault = new Property(6, Boolean.class, "isDefault", false, "IS_DEFAULT");
    };


    public AddressDao(DaoConfig config) {
        super(config);
    }
    
    public AddressDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ADDRESS' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'USERNAME' TEXT," + // 1: username
                "'MOBILE_NO' TEXT," + // 2: mobileNo
                "'AREA' TEXT," + // 3: area
                "'STREET' TEXT," + // 4: street
                "'DETAIL' TEXT," + // 5: detail
                "'IS_DEFAULT' INTEGER);"); // 6: isDefault
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ADDRESS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Address entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String mobileNo = entity.getMobileNo();
        if (mobileNo != null) {
            stmt.bindString(3, mobileNo);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(4, area);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(5, street);
        }
 
        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(6, detail);
        }
 
        Boolean isDefault = entity.getIsDefault();
        if (isDefault != null) {
            stmt.bindLong(7, isDefault ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Address readEntity(Cursor cursor, int offset) {
        Address entity = new Address( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mobileNo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // area
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // street
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // detail
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0 // isDefault
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Address entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMobileNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setArea(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStreet(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDetail(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsDefault(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Address entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Address entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
