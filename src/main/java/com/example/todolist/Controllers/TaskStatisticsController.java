import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/taskstatistics")
public class TaskStatisticsController {
    @Autowired
    private TaskStatisticsRepository taskStatisticsRepository;

    @GetMapping
    public List<TaskStatisticsVM> getAllTaskStatistics() {
        return taskStatisticsRepository.findAll();
    }
}
