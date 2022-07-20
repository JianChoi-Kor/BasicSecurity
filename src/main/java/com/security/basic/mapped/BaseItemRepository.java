package com.security.basic.mapped;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BaseItemRepository {

    private final JPAQueryFactory queryFactory;

    com.security.basic.mapped.QBaseItem qItemA = com.security.basic.mapped.QItemA.itemA._super;
    com.security.basic.mapped.QBaseItem qItemB = com.security.basic.mapped.QItemB.itemB._super;

    public List<ItemDto> itemAList() {
        return queryFactory.select(Projections.constructor(ItemDto.class,
                        qItemA.id,
                        qItemA.name
                ))
                .from(qItemA)
                .fetch();
    }

    public List<ItemDto> itemBList() {
        return queryFactory.select(Projections.constructor(ItemDto.class,
                        qItemB.id,
                        qItemB.name
                ))
                .from(qItemB)
                .fetch();
    }
}
