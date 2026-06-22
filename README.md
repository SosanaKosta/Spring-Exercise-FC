This application provides comprehensive project workflows, task assignments, automated email dispatches, real-time logging, commenting, and local file storage systems.




**Architectural Overview & Design Patterns**

The system implements a strict Separation of Concerns (SoC) approach divided into distinct semantic tiers:

Presentation Tier (Index.html): Single Page Application structure driven by state-based routing.

Controller Layer (@RestController): Exposes RESTful endpoints, delegates payload operations, and maps explicit authentication filtering protocols.

Service Tier (@Service): Encapsulates core business logic transactional processing boundaries (@Transactional).

Data Transfer Objects (DTOs): Used throughout the application (e.g., CommentDTO, AttachmentDTO) to completely decouple database entities from presentation JSON layouts. This completely prevents Jackson InvalidDefinitionException issues caused by Hibernate's ByteBuddy dynamic lazy proxies.

Repository Tier (@Repository): Standard Spring Data JPA interfaces extending JpaRepository.




**Authentication Specification**

The application implements a stateless HTTP Basic Authentication protocol.

User profiles submit their credentials encoded via a standard Base64 string format injected inside the Authorization request header packet configuration:
The system evaluates credentials directly against a CustomUserDetailsService implementation mapped to database tables on every inbound API call, bypassing session trackers entirely.
Production Assumption Note: Basic Authentication exposes credentials if intercepted over unencrypted communication. Implementing this strategy requires pairing it with an active TLS/SSL context wrapper (HTTPS).

**Controller Layer Integration Tests (@SpringBootTest + MockMvc)**

Focus: Verifies endpoint routing, @Valid body payload validations, JSON mapping, and Spring Security authentication filters.


