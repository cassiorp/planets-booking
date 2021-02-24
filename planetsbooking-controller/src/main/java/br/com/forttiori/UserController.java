package br.com.forttiori;

import br.com.forttiori.DTO.UserRequestDTO;
import br.com.forttiori.DTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO save(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return this.userService.save(userRequestDTO);
    }

    @GetMapping
    public List<UserResponseDTO> findAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nome", required = false) String nome
    ) {
        return this.userService.findAll(page, nome);
    }

    @GetMapping("/{id}")
    public UserResponseDTO find(@Valid  @PathVariable String id) {
        return this.userService.findByIdResponse(id);
    }

    @DeleteMapping
    public List<User> deleteMany(@RequestParam("ids") List<String> ids) {
        return userService.deleteMany(ids);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public void options(HttpServletResponse response) {
        response.setHeader("Allow", "POST,GET,DELETE");
    }

}
