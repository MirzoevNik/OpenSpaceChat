package com.mirzoevnik.openspacechat.dao;

import java.io.Serializable;

/**
 * @author MirzoevNik
 * @param <PK> primary key
 */
public interface Identified<PK extends Serializable> {

    /**
     * @return id of object
     */
    PK getId();
}

