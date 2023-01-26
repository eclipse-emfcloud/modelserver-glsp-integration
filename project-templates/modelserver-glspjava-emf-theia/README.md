# Eclipse EMF.cloud - Project Template:<br> 💾 Model Server ● 🖥️ Java GLSP-Server ● 🗂️ EMF ● 🖼️ Theia

This folder contains a simple _project template_ to get you started quickly for your model editor implementation based on the [EMF.cloud Model Server](https://github.com/eclipse-emfcloud/emfcloud-modelserver) and [GLSP](https://github.com/eclipse-glsp/glsp).
It provides the initial setup of the package architecture and environment for a model editor that uses ...

- 💾 The [Java Model Server framework](https://github.com/eclipse-emfcloud/emfcloud-modelserver)
- 🖥️ The [Java GLSP server framework](https://github.com/eclipse-glsp/glsp-server)
- 🔗 The [EMF.cloud Model Server GLSP Integration framework](https://github.com/eclipse-emfcloud/modelserver-glsp-integration)
- 🗂️ An [EMF](https://www.eclipse.org/modeling/emf/)-based source model
- 🖼️ The [Theia integration](https://github.com/eclipse-glsp/glsp-theia-integration) to make your editor available as Theia application

To explore alternative project templates or learn more about developing pure GLSP-based diagram editors, please refer to the [Getting Started](https://www.eclipse.org/glsp/documentation/gettingstarted) guide of the Eclipse GLSP project.

## Project structure

This project is structured as follows:

- [`client`](client)
  - [`tasklist-browser-app`](client/tasklist-browser-app): browser client application that integrates the basic Theia plugins and the tasklist specific glsp plugins
  - [`tasklist-glsp`](client/tasklist-glsp): diagram client configuring the views for rendering and the user interface modules
  - [`tasklist-theia`](client/tasklist-theia): glue code for integrating the editor into Theia
  - [`workspace`](client/workspace): contains an example file that can be opened with this diagram editor
- [`glsp-server`](glsp-server)
  - [`org.eclipse.emfcloud.integration.example.glspjavaserver`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver): dependency injection module of the server, diagram configuration and Model Server access
  - [`org.eclipse.emfcloud.integration.example.glspjavaserver.handler`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/handler): handlers for the diagram-specific actions
  - [`org.eclipse.emfcloud.integration.example.glspjavaserver.model`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/model): all source model, graphical model and model state related files
  - [`org.eclipse.emfcloud.integration.example.glspjavaserver.launch`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/launch): contains the Java GLSP server launcher
  - [`org.eclipse.emfcloud.integration.example.glspjavaserver.palette`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/palette): custom palette item provider
- [`model-server`](model-server)
  - [`org.eclipse.emfcloud.integration.example.modelserver`](model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver): dependency injection module of the Model Server, resource manager and resource related responsibilities
  - [`org.eclipse.emfcloud.integration.example.modelserver.commands`](model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver/commands): contains the semantic EMF commands (for `TaskList` semantic model changes) and compound EMF commands (semantic & notation model changes chained)
  - [`org.eclipse.emfcloud.integration.example.modelserver.contributions`](model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver/contributions/): contains the corresponding command contributions
  - [`org.eclipse.emfcloud.integration.example.modelserver.launch`](model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver/launch): contains the Java Model Server launcher

The most important entry points are:

- [`client/tasklist-glsp/src/di.config.ts`](client/tasklist-glsp/src/di.config.ts): dependency injection module of the client
- [`client/tasklist-browser-app/package.json`](client/tasklist-browser-app/package.json): Theia browser application definition
- [`glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/TaskListDiagramModule.java`](glsp-server/src/main/java/org/eclipse/emfcloud/integration/example/glspjavaserver/TaskListDiagramModule.java): dependency injection module of the GLSP server
- [`model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver/TaskListModelServerModule.java`](model-server/src/main/java/org/eclipse/emfcloud/integration/example/modelserver/TaskListModelServerModule.java): dependency injection module of the Model Server

> **_NOTE:_**&nbsp; Due to bug [GLSP-666](https://github.com/eclipse-glsp/glsp/issues/666) the launch configurations for the `Theia Backend` might not work as expected when using Windows. Unfortunately there is currently no work-around and if you encounter this bug you won't be able to debug the Theia backed.

## Prerequisites

The following libraries/frameworks need to be installed on your system:

- [Node.js](https://nodejs.org/en/) `>= 14`
- [Yarn](https://classic.yarnpkg.com/en/docs/install#debian-stable) `>=1.7.0`
- [Java](https://adoptium.net/temurin/releases) `>=11`
- [Maven](https://maven.apache.org/) `>=3.6.0`

The examples are heavily interweaved with Eclipse Theia, so please also check the [prerequisites of Theia](https://github.com/eclipse-theia/theia/blob/master/doc/Developing.md#prerequisites).

> **Remark:** This project-template relies on the latest published versions of Model Server, GLSP and the Model Server GLSP integration. This means, it is built separately from the integration framework.

## VS Code workspace

For both the client and the server part of this example we use [Visual Studio Code](https://code.visualstudio.com/).
It is of course possible to use the [Eclipse IDE](https://www.eclipse.org/ide/) for the server or any other IDE or text editor.

To work with and debug the source code in VS Code a dedicated [VS Code Workspace](modelserver-glspjava-emf-theia-example.code-workspace) is provided.
This workspace includes the `client`, `glsp-server` and `model-server` sources and offers dedicated launch configurations to run and debug the example application.

To open the workspace start a VS Code instance and use the `Open Workspace from File..` entry from the `File` menu.
Then navigate to the directory containing the workspace file and open the `modelserver-glspjava-emf-theia-example.code-workspace` file.

For a smooth development experience we recommend a set of useful VS Code extensions. When the workspace is first opened VS Code will ask you whether you want to install those recommended extensions.
Alternatively, you can also open the `Extension View` (Ctrl + Shift + X) and type `@recommended` into the search field to see the list of `Workspace Recommendations`.

## Building the example

The server component is built with `maven` and the client component is built with `yarn`.
A convenience script to build both is provided.
To build all components execute the following in the directory containing this README:

```bash
yarn build
```

In addition, it is also possible to build the components individually:

```bash
# Build only the client
yarn build:client

# Build only servers
yarn build:servers
```

Or you can use the available VS Code Tasks configured in the [VS Code workspace](modelserver-glspjava-emf-theia-example.code-workspace) (via Menu _Terminal > Run Task..._)

- `Build TaskList Servers`
- `Build TaskList GLSP Client example`

## Running the example

To start the Theia browser application with the integrated TaskList example, execute the following command in the root directory

```bash
yarn start
```

This will launch the example in the browser with an embedded Model Server and GLSP server on [localhost:3000](http://localhost:3000).

To debug the involved components, the [VS Code workspace](modelserver-glspjava-emf-theia-example.code-workspace) offers launch configs, available in the `Run and Debug` view (Ctrl + Shift + D).
Here you can choose between different launch configurations:

- `Launch TaskList Model Server [DEBUG]`<br>
  This config can be used to manually launch the `TaskList Model Server` java process.
  Breakpoints in the source files of the `model-server` directory will be picked up.
  In order to use this config, the Theia application backend has to be launched after startup of the Model Server, as it pings the Model Server and if it is already running it won't startup an embedded instance.
  If the Model Server is started via this launch config, it is possible to consume code changes immediately in the running instance via `Hot Code Replace` in the Debug toolbar.
- `Launch TaskList GLSP Server [DEBUG]`<br>
  This config can be used to manually launch the `TaskList GLSP Server` java process.
  Breakpoints in the source files of the `glsp-server` directory will be picked up.
  In order to use this config, the Theia application backend has to be launched in `External` server mode (see `Launch TaskList Theia Backend (External GLSP Server)`).
  If the GLSP server is started via this launch config, it is possible to consume code changes immediately in the running instance via `Hot Code Replace` in the Debug toolbar.
- `Launch TaskList Theia Backend [DEBUG] (External GLSP Server)`<br>
  This config launches the Theia browser backend application but does not start the GLSP server as embedded process.
  Breakpoints in the source files of the `client/**/node` directories will be picked up.
  It expects that the GLSP Server process is already running and has been started externally with the `Launch TaskList GLSP Server` config.
  The Model Server instance can be started prior to this launch config (as described above), otherwise an embedded instance is started.
- `Launch TaskList Theia Backend [DEBUG] (Embedded GLSP Server)`<br>
  This config launches the Theia browser backend application and will start the GLSP server as embedded process which means you won't be able to debug the GLSP Server source code.
  Breakpoints in the source files of the `client/**/node` directories will be picked up.
  The Model Server instance can be started prior to this launch config (as described above), otherwise an embedded instance is started.
- `Launch Theia Frontend [DEBUG]`<br>
  Launches a Google Chrome instance, opens the Theia browser application at `http://localhost:3000` and will automatically open an example workspace that contains a `example.tasklist` file.
  Double-click the file in the `Explorer` to open it with the `TaskList Diagram Editor`.
  Breakpoints in the source files of the `client/**/browser` directories will be picked up.

> **_Hint:_**&nbsp; If the Java servers cannot be started via launch configuration or VSCode task, please try to execute the command `Java: Clean Java Language Server Workspace` via the VSCode command palette.

### Watch the TypeScript packages

To run TypeScript in watch-mode so that TypeScript files are compiled as you modify them via CLI:

```bash
yarn watch
```

or the VSCode task `Watch TaskList GLSP Client example`.

## Next steps

Once you are up and running with this project template, we recommend to refer to the [Getting Started](https://www.eclipse.org/glsp/documentation) to learn how to

- ➡️ Add your custom [source model](https://www.eclipse.org/glsp/documentation/sourcemodel) instead of using the example model
- ➡️ Define the diagram elements to be generated from the source model into the [graphical model](https://www.eclipse.org/glsp/documentation/gmodel)
- ➡️ Make the diagram look the way you want by adjusting the [diagram rendering and styling](https://www.eclipse.org/glsp/documentation/rendering)

## More information

For more information, please visit the [EMF.cloud Umbrella repository](https://github.com/eclipse-emfcloud/emfcloud), [EMF.cloud Website](https://www.eclipse.org/emfcloud/), [Eclipse GLSP Umbrella repository](https://github.com/eclipse-glsp/glsp) and the [Eclipse GLSP Website](https://www.eclipse.org/glsp/).
If you have questions, please raise them in the [EMF.cloud discussions page](https://github.com/eclipse-emfcloud/emfcloud/discussions) or the [Eclipse GLSP discussions page](https://github.com/eclipse-glsp/glsp/discussions) and have a look at our [communication and support options](https://www.eclipse.org/emfcloud/contact/).
