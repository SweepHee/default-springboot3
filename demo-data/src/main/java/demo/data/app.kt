package demo.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("demo")
class DataApplication {
    companion object {
        const val appName = "demo-data"
    }
}
