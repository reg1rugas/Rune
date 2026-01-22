# Project Rune: Work Breakdown Structure

## **Total Modules:** 8
---

## Phase 1: Frontend Development (Visuals & Interaction)

### Module 1: Frontend Foundation & Canvas Core
**Objective:** Initialize the application and create the drawing surface.
* [ ] **1.1 Project Initialization**
    * Initialize SPA (React/Vue/Angular) with TypeScript.
    * Set up TailwindCSS for UI styling.
    * Configure ESLint and Prettier.
* [ ] **1.2 Canvas Architecture**
    * Set up HTML5 Canvas element (or initialize Fabric.js/Konva.js).
    * Implement "Infinite Canvas" logic (Zoom In/Out, Panning/Scrolling).
    * Handle window resize events to maintain canvas aspect ratio.
* [ ] **1.3 Input Event Listener System**
    * Create a unified handler for Mouse and Touch events (`mousedown`, `touchstart`, etc.).
    * Implement coordinate mapping (Screen Coords -> Canvas Coords).

### Module 2: Whiteboard Toolset & Logic
**Objective:** Enable the user to create content on the board.
* [ ] **2.1 Drawing Tools**
    * **Pencil/Brush:** Implement freehand drawing with Bézier curve smoothing.
    * **Eraser:** Logic to remove objects or clear pixels.
* [ ] **2.2 Shape & Text Engine**
    * Implement shape rendering (Rectangle, Circle, Line/Arrow).
    * Implement Text tool (Input overlay -> Render to canvas).
* [ ] **2.3 Local State Management**
    * Set up a global store (Redux Toolkit / Zustand) to hold the array of drawing elements.
    * **History Manager:** Implement Undo/Redo stacks using the Command Pattern.

### Module 3: Frontend Networking Client
**Objective:** Prepare the client to send and receive data.
* [ ] **3.1 WebSocket Client Integration**
    * Install `sockjs-client` and `@stomp/stompjs`.
    * Create a reusable `WebSocketService` class (Connect, Disconnect, Subscribe).
* [ ] **3.2 Data Protocol Definition**
    * Define the JSON structure for drawing actions.
    * *Example:* `{ "type": "DRAW", "tool": "pen", "points": [...], "color": "#000" }`
* [ ] **3.3 Optimistic UI**
    * Implement logic to render local changes immediately before server confirmation to ensure zero latency.

---

## Phase 2: Backend Development (Spring Boot)

### Module 4: Backend Infrastructure & Security
**Objective:** Set up the Spring Boot server and manage access.
* [ ] **4.1 Spring Boot Setup**
    * Initialize project with dependencies: `Spring Web`, `Spring WebSocket`, `Lombok`, `Spring Data JPA/MongoDB`.
* [ ] **4.2 Database Modeling**
    * **User Entity:** ID, Username, Email, Password Hash.
    * **Board Entity:** ID (UUID), Name, Owner, CreatedAt.
    * **BoardData Entity:** JSON Blob storage for canvas elements.
* [ ] **4.3 Authentication & Authorization**
    * Implement Spring Security.
    * Set up JWT (JSON Web Token) generation and validation filter.
    * Create REST APIs for `Auth` (Login/Signup) and `Board Management` (Create/List Boards).

### Module 5: Real-Time Signaling Engine
**Objective:** Enable instant data transfer between users.
* [ ] **5.1 WebSocket Configuration**
    * Implement `WebSocketMessageBrokerConfigurer`.
    * Configure STOMP endpoints:
        * `/ws` (Handshake endpoint).
        * `/topic` (Public broadcasts).
        * `/app` (Client-to-Server messages).
* [ ] **5.2 Messaging Controllers**
    * Create `SocketController` to handle drawing events.
    * Implement logic to broadcast incoming drawing data to specific `Board ID` topics.
* [ ] **5.3 Session Management**
    * Track active socket sessions mapped to User IDs.
    * Handle `SessionDisconnectEvent` to clean up resources.

---

## Phase 3: Integration & Production

### Module 6: Fullstack Synchronization
**Objective:** Connect Frontend and Backend for multi-user experience.
* [ ] **6.1 Remote Rendering System**
    * Frontend: Subscribe to `/topic/board/{id}`.
    * Frontend: Logic to interpret incoming JSON and render it on the canvas *without* adding it to the Undo stack.
* [ ] **6.2 Live Presence (Cursors)**
    * **Frontend:** Broadcast mouse coordinates (throttled to 50ms).
    * **Backend:** Pass-through endpoint for cursor positions.
    * **UI:** Render colored flags/cursors for remote users.
* [ ] **6.3 Connection Resilience**
    * Implement auto-reconnect logic on the frontend.
    * Handle "Late Joiner" scenario (fetch current board state upon joining).

### Module 7: Persistence & State Recovery
**Objective:** Save work and load it later.
* [ ] **7.1 State Serialization**
    * Frontend: "Export to JSON" functionality.
    * Backend: API endpoint `POST /api/boards/{id}/save`.
* [ ] **7.2 Data Storage Strategy**
    * Implement Hybrid approach:
        * **Hot Data:** Keep active board state in memory (Redis or JVM Map) for speed.
        * **Cold Data:** Flush state to Database (Postgres/Mongo) on disconnect or interval (e.g., every 1 min).
* [ ] **7.3 Board Loading**
    * Create API `GET /api/boards/{id}` to fetch history.
    * Frontend: Hydrate the Redux store/Canvas from the fetched JSON.

### Module 8: Optimization & Deployment
**Objective:** Polish and ship the application.
* [ ] **8.1 Performance Optimization**
    * **Batching:** Group high-frequency events (like freehand drawing) before sending.
    * **Render Tuning:** Use `requestAnimationFrame` effectively; ensure off-screen canvas elements aren't re-rendered.
* [ ] **8.2 Export Features**
    * Add "Export as PNG" and "Export as PDF" to the frontend toolbar.
* [ ] **8.3 DevOps & CI/CD**
    * **Docker:** Create `Dockerfile` for Backend and `nginx` container for Frontend.
    * **Pipeline:** Set up GitHub Actions for automated build and test.
    * **Deploy:** Push to cloud provider (AWS EC2, Render, or Railway).
