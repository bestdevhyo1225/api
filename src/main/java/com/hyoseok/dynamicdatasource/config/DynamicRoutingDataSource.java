package com.hyoseok.dynamicdatasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private CircularReadList<String> circularReadList;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        List<String> readList = targetDataSources.keySet().stream()
                .map(Object::toString)
                .filter(key -> key.contains("read"))
                .collect(Collectors.toList());

        circularReadList = new CircularReadList<>(readList);
    }

    /*
    * ----- determineCurrentLookupKey() 메소드 -----
    * DataSource 연결이 필요할 때, DataSourceMap에서 어떤 DataSource를 사용할지, Key를 찾아주는 역할을 한다.
    * TransactionSynchronizationManager.isCurrentTransactionReadOnly()을 통해서
    * 현재 진행중인 트랜잭션이 ReadOnly 인지 여부를 판단하여 Master / Slave DataSource를 분기해줄수 있다.
    * */
    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        String databaseType = isReadOnly ? circularReadList.getOne() : "write";
        log.debug("determineCurrentLookupKey() : {}", databaseType);
        return databaseType;
    }
}
