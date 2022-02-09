## API
MemberAPIController
````
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberQueryRepository memberQueryRepository;

    @PostMapping("/api/members/save")
    public MemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {

        log.info("saveMember완료");
        Member member = Member.createMember(request.getName(),null, request.getLoginId(), request.getPassword());
        Long id = memberService.join(member);
        return new MemberResponse(id);
    }

    @GetMapping("/api/members")
    public Result findMember() {
        List<MemberDTO> result = memberQueryRepository.findMemberDTO();
        return new Result(result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
        private String loginId;
        private String password;
    }

    @Data
    static class MemberResponse {
        private Long id;

        public MemberResponse(Long id) {
            this.id = id;
        }
    }
}
````
MemberDTO
````
@Data
public class MemberDTO {
    private String name;
    private String loginId;

    @QueryProjection
    public MemberDTO(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
    }
}
````
MemberQueryRepository
````
@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<MemberDTO> findMemberDTO() {
        return queryFactory
                .select(new QMemberDTO(member.name, member.loginId))
                .from(member)
                .fetch();
    }
}
````