@RestController
public class CommentController {
	 @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // --------------------- //

    @RequestMapping(path="/add-test-comment", method = RequestMethod.GET)
    public void addTestComment(Project project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getUserByUsername(authentication.getName());

        Comment comment = new Comment();
        comment.setAuthor(loggedUser);
        comment.setProject(project);
        comment.setMessage("Hello world");
        comment.setLikes([]);
        commentService.create(comment);
    }

    // TODO : path

    @Operation(summary = "Récupération des commentaires pour un projet")
    @RequestMapping(path="/", method= RequestMethod.GET)

    @Operation(summary = "Récupération des commentaires écrit par un user")
    @RequestMapping(path="/", method= RequestMethod.GET)

    @Operation(summary = "Récupération des commentaires likés par un user") 
    @RequestMapping(path="/", method= RequestMethod.GET)

    @Operation(summary = "Création d'un commentaire par un user")
    @RequestMapping(path="/", method= RequestMethod.POST)

    @Operation(summary = "Ajout d'un like sur un commentaire par un user")
    @RequestMapping(path="/", method= RequestMethod.POST)

    @Operation(summary = "Modification d'un commentaire par l'auteur")
    @RequestMapping(path="/", method= RequestMethod.PUT)
    public Object updateComment(@Valid @RequestBody Comment comment) throws Exception {
        Authentication loggedUser = this.securityService.getLoggedUser();
        User user = userService.getUserByUsername(loggedUser.getName());

        if (user != comment.getAuthor()){
            throw new Exception("Vous n'êtes pas l'auteur de ce commentaire");
        }

        return commentService.update(comment);
    }

    @Operation(summary = "Suppression d'un commentaire par l'auteur") 
    @RequestMapping(path="/", method= RequestMethod.DELETE)  
}