package com.wxj.service;

import com.wxj.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    Type getTypeByName(String name);

    void deletType(Long id);

    List<Type> listType();

    List<Type> listType(Integer size);
}
