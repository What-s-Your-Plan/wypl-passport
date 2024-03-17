package com.cdd.sangchupassport;

import com.cdd.sangchupassport.token.PassportToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 각 변수의 의미는 다음과 같습니다.<p>
 * <p>
 * id: `Passport`의 식별자입니다. 해당 식별자로 중복 인증을 방지합니다.<p>
 * memberId: 인증을 요청하는 사용자의 식별자입니다.<p>
 * expiredTime: `Passport`의 만료시간 입니다. 만료 시간이 지나면 해당 객체로는 인증할 수 없습니다.<p>
 * destinations: 사용자의 요청이 도달할 수 있는 목적지들 입니다.  0번 인덱스부터 순차적으로 접근해야 해고 도착하게 되면 해당 목적지를 삭제합니다. 잘못된 목적지에 도달할 시 인증할 수 없습니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Passport {
    @JsonProperty("passport_id")
    private String id;

    @JsonProperty("member_id")
    private int memberId;

    @JsonProperty("expired_time")
    private long expiredTime;

    @JsonProperty("destinations")
    private List<String> destinations;

    public boolean isEqualToDestination(String destination) {
        return destinations.isEmpty() || !destination.equals(destinations.get(0));
    }

    public void stamp(CrudRepository<PassportToken, String> repository) {
        destinations.remove(0);
        if (destinations.isEmpty()) {
            repository.save(PassportToken.from(id));
        }
    }
}
