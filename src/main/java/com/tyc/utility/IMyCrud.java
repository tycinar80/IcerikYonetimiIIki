package com.tyc.utility;

import java.util.List;
import java.util.Optional;

public interface IMyCrud<T, ID> extends IMyRepository<T, ID> {
    <S extends T> S save(S entity); // Verilen entity nesnesi kayit edeilecek

    <S extends T> Iterable<S> save(Iterable<S> entities); // Bir liste seklinde nesneleri gondererek kayit edebiliriz

    void deleteById(ID id); // Generic olarak verilen ID'nin tipine gore id ileterek silme islemi yapilir

    void delete(T entity);

    Optional<T> findById(ID id); // Id'ye gore ilgili kayit optional olarak doner

    boolean existById(ID id); // Id'si verilen kaydin olup olmadiği donulur(true/false)

    List<T> findAll(); // Tum kayitlari listeler

    List<T> findByColumnAndValue(String column, Object value); // Kolon ve degeri verilen kayitlari eslestirerek
    // listeler

    List<T> findByEntity(T entity); // Nesnenin kenidisi sorgulama yapilacak alanlari ile doldurularak islenir
}
