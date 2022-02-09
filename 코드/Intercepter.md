## Intercepter
LogIntercepter
````
@Slf4j
public class LogIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(RequestConst.Log_Id, uuid);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object logId = request.getAttribute(RequestConst.Log_Id);

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error", ex);
        }
    }
}
````
LoginCheckIntercepter
````
@Slf4j
public class LoginCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.Login_Member) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}
````
RequestConst
````
public class RequestConst {
    public static final String Log_Id = "logId";
}
````
WebConfig
````
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogIntercepter())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "error");

        registry.addInterceptor(new LoginCheckIntercepter())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error*", "/api/**", "/", "/login", "/logout", "/members/new");
    }
}
````
MemberService
````
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member) {
        //중복회원검증
        validateDuplicateMember(member);
        log.info("중복검증완료");
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    //회원전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원단일조회
    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (!findMember.isPresent()) {
            throw new NoMemberException("회원이 존재하지 않습니다.");
        }
        return findMember.get();
    }

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> Member = memberRepository.findByName(member.getName());
        if (!Member.isEmpty()) {
            throw new ExistMemberException("이미 존재하는 회원입니다.");
        }
    }

}
````
LoginController
````
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login/createLoginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("form") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

        log.info("error = {}", bindingResult);
        log.info("redirectURL = {}", redirectURL);
        if (bindingResult.hasErrors()) {
            return "login/createLoginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/createLoginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.Login_Member, loginMember);
        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
````
SessionConst
````
public class SessionConst {
    public static final String Login_Member = "loginMember";
}
````