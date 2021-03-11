package com.cx.reactor.test;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PracticeTest {
    @Test
    /**
     * 根据属性找出相同对象
     */
    public void testFindSame() {
        Replica replica1 = Replica.builder().axId(2).vzId(3).abc("replica1").nodeId(10).build();
        Replica replica2 = Replica.builder().axId(2).vzId(3).abc("replica2").nodeId(10).build();
        Replica replica3 = Replica.builder().axId(2).vzId(1).abc("replica3").nodeId(10).build();
        Replica replica4 = Replica.builder().axId(3).vzId(1).abc("replica4").nodeId(10).build();
        Replica replica5 = Replica.builder().axId(3).vzId(1).abc("replica5").nodeId(10).build();
        Replica replica6 = Replica.builder().axId(5).vzId(1).abc("replica6").nodeId(10).build();

        List<Replica> replicas = Arrays.asList(replica1, replica2, replica3, replica4, replica5, replica6);

        List<Replica> illegalReplicas = replicas.stream().collect(Collectors.groupingBy(re -> re.getVzId() + ";" + re.getAxId()))
                .values()
                .stream()
                .filter(l -> l.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("collect1:{}", illegalReplicas);
        log.info("size :{}", illegalReplicas.size());
        List<String> illegalReplicaAzIds = replicas.stream().collect(Collectors.groupingBy(replica -> replica.getAxId() + ";" + replica.getVzId(),
                Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1L)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<Integer, Map<Integer, List<Replica>>> collect = replicas.stream().collect(Collectors.groupingBy(Replica::getAxId, Collectors.groupingBy(Replica::getVzId)));
        List<Replica> collect1 = collect.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .filter(list -> list.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Builder
    @Data
    public static class Replica {
        int axId;
        int vzId;
        String abc;
        int nodeId;
    }
}
