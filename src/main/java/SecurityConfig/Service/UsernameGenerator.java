package SecurityConfig.Service;

import SecurityConfig.UserRepository.repo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UsernameGenerator {
    private final repo repo;
    private static final int Max_attempts = 5;
    private static final int Max_strategy = 5;

    private List<String> generateUsernames(String firstName, String middleName, String lastName) {

        String first = sanitize(firstName);
        String middle = sanitize(middleName);
        String last = sanitize(lastName);


        List<String> users = new ArrayList<>();

        users.add(first);

        users.add(first + middle + last);

        users.add(first + middle + "_" + last);
        users.add(first + middle);
        users.add(first + "_" + middle);

        users.add(last + first);

        users.add(first + "." + last);

        users.add(first + last.charAt(0));

        users.add(first.charAt(0) + last);

        users.add(first + LocalDate.now().getYear());

        return users;
    }

    private String sanitize(String value) {

        return value
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");
    }

    public String UsernameBuilder(String firstName, String middleName, String lastName) {

        List<String> username = generateUsernames(firstName, middleName, lastName);

        for (String users : username) {
            if (!repo.existsByUsername(users)) {
                return users;
            }
        }
        return generateWithRandomSuffix(firstName);

    }

    private String generateWithRandomSuffix(String base) {
        for (int i = 0; i < 20; i++) {

            String username = base +
                    ThreadLocalRandom.current().nextInt(1000, 10000);

            if (!repo.existsByUsername(username)) {
                return username;
            }
        }

        throw new IllegalStateException("Unable to generate a unique username.");
    }

}


