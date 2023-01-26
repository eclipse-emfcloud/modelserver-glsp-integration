# Eclipse EMF.cloud Model Server GLSP Integration [![build-status](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/emfcloud/job/eclipse-emfcloud/job/modelserver-glsp-integration/job/main/)](https://ci.eclipse.org/emfcloud/job/eclipse-emfcloud/job/modelserver-glsp-integration/job/main/)

Contains the base integration code for GLSP diagrams in an Model Server aware editor environment.
Throughout the repository the abbreviation `EMS` is used as a prefix for base classes and stands for **E**MF.cloud **M**odel **S**erver.

## P2 Update Sites [![publish-status-p2](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/emfcloud/job/deploy-emfcloud-modelserver-glsp-integration-p2/&label=p2)](https://ci.eclipse.org/emfcloud/job/deploy-emfcloud-modelserver-glsp-integration-p2/)

- <i>Snapshots: </i> https://download.eclipse.org/emfcloud/modelserver-glsp-integration/p2/nightly/

## Maven Repositories [![publish-status-m2](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/emfcloud/job/deploy-emfcloud-modelserver-glsp-integration-m2/&label=m2)](https://ci.eclipse.org/emfcloud/job/deploy-emfcloud-modelserver-glsp-integration-m2/)

- <i>Snapshots: </i> https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/emfcloud/modelserver/glsp/

## Prerequisites

The following libraries/frameworks need to be installed on your system:

|                                                                              | Version   | Remark                                             |
| ---------------------------------------------------------------------------- | --------- | -------------------------------------------------- |
| [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) | `>= 11`   | We support the two latest LTS versions (11 and 17) |
| [Maven](https://maven.apache.org/)                                           | `>=3.6.0` |                                                    |

## Project Template

This repository includes a project-template that combines Model Server, Java GLSP-Server, EMF model inside a Theia application.
See its [README](./project-templates/modelserver-glspjava-emf-theia/README.md) for further details.

> **Remark:** This project-template relies on the latest published versions of Model Server, GLSP and the Model Server GLSP integration. This means, it is built separately from the integration framework.

## Where to find the sources?

In addition to this repository, the related source code can be found here:

- <https://github.com/eclipse-emfcloud/emfcloud-modelserver>
- <https://github.com/eclipse-glsp/glsp-server>

## More information

For more information, please visit the [EMF.cloud Website](https://www.eclipse.org/emfcloud/). If you have questions, contact us on our [discussions page](https://github.com/eclipse-emfcloud/emfcloud/discussions) and have a look at our [communication and support options](https://www.eclipse.org/emfcloud/contact/).
