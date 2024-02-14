# Coucal

A mixed content API for collaborative data.

## Overview

Coucal is an application that provides support for managing a variety of collaborative activities through a mixed
content API.

### What is mixed content?

The Coucal API for mixed content can support multiple types of data used for collaboration. Coucal is based on
interoperability standards including iCalendar and vCard, and supports many derivatives of the following base data
types:

* Actions
* Availability - scheduling resource and individual availability for collaboration
* Entities - capturing details of collaborative resources and actors
* Events - scheduling of public and private events
* Issues - tracking actionable tasks and activities
* Metrics
* Notes - recording shared notes and outcomes of collaborations
* Requests

### Content Structure

Content payloads are loosely based on the properties found in the iCalendar and vCard specifications. When creating
or consuming event-based content you will recognise the fields applicable to the iCalendar `VEVENT` component:

```json
{
  "dtstart": "20231104T093000",
  "dtend": "20231104T103000",
  "summary": "Feature planning for next iteration",
  "concept": "https://ical4j.org/event-types/MEETING"
}
```

### Repositories

Collaborative data is managed via content repositories that support typical CRUD operations, as well as a powerful
content filtering language.

### Workspaces

Workspaces provide isolation between repositories intended for different contexts. For example, you might manage
multiple teams that each have their own collaborative activities and processes. Providing a workspace for each team
ensures their data and activities are isolated from the other teams.

### Channels

Interoperability with other collaborative tools is core to the Coucal mission. Channels provide an extensible design
for managing the flow of data from and to repositories. A common scenario in scheduling is to use Email to transport
invitations and replies to/from participants. Coucal supports such interactions via Channels, in addition to other
data flows such as Webhooks and Websub.

### Subscriptions

TBD.

### Filters

TBD.

### Roles

TBD.

### Handlers

Handlers perform specific actions in response to events in Coucal. Typically, handlers are used to publish, transform and ingest content
from configured channels.

#### SMTP

The SMTP Handler is responsible for all email notifications produced in Coucal. This may include sending invitations and updates of events
to participants, notification of changes to issues, requests, etc.

Channel configurations are used to define the rules dictating the behaviour of the SMTP Handler, with default channels provided for each workspace:

- When an Event is created, updated or deleted, email the changes to participants (i.e. extract `ATTENDEE` and `ORGANIZER` properties as recipients).
- When an Observance is created, updated or deleted, email to all workspace members.
- When an Action, Issue or Request are created, updated or deleted, email to assignees and watchers (i.e. extract `ATTENDEE` and `ORGANIZER` properties as recipients).

#### WebSub

The WebSub Handler provides support for publishing changes to a WebSub topic. For example, public events typically don't specify attendees but you still
require a medium for sharing updates, etc.

The WebSub handler also supports limited content transformations to allow for publishing content in other formats (e.g. Atom/RSS feed).

No default configurations are provided for WebSub channels, however possible examples may include:

- When an Event is created or updated with no attendees, publish to a WebSub topic.
- When a Note is created or updated, publish as Atom to a WebSub topic.

#### Webmention

The Webmention handler is responsible for publishing links to content where a Webmention endpoint is provided. This can be useful where
actions, issues, etc. are linked to internal and external events and requests, in order to reflect in the original content.

No default channels are provided for Webmentiones, but possible examples could include:

- When an Action, Event, Issue, Note, etc. is created or updated, and has a `RELATED-TO` property, check the referenced component for a Webmention link. Publish the Webmention details to the provided endpoint.


#### XMPP

The XMPP handler supports notifications over XMPP. This may include direct messages to participants or workspace members, or notifications
to multi-user chats (rooms).

No default channels are specified for XMPP, but possible examples are as follows:

- When an Action, Event, Issue, etc. is created or updated, check for participants with corresponding entities that include `XMPP` properties. For all identified entities send a notification over XMPP.
- When a new Request is raised, check workspace members for XMPP details and send notification.
- When a new Request is raised, send an XMPP notification to a configured room.



===

Coucal is designed to provide backend functionality for a wide variety of calendar, task and workflow applications. 
If you consider the current landscape of calendaring and work management applications you will see that for a long time 
it has been dominated by a few major products that have opinionated approaches to team collaboration. Coucal is provided 
to hopefully provide an alternative to how you build and customise your collaborative tooling and work management
processes.

## Design

The Coucal architecture is based on the principles of interoperability and sharing. We want our data to be open and 
standardised, such that we can achieve maximum integration and collaboration with other tools and platforms. We also 
want to ensure that sharable content is revisable and consistent such that it doesn't become stale or misinformation.

The core concepts in the Coucou design are as follows:

### Repository

Repositories are where user-generated content is stored. A Repository may be restricted to a specific type of content
(e.g. events, tasks, etc.), or possibly a combination of multiple content types.

List abridged contents:

    curl https://api.coucal.net/v1/repositories/1/content

Create a new resource:

    curl -X https://api.coucal.net/v1/repositories/1/content
    {
        "template": "meeting"
    }

Get resource details:

    curl https://api.coucal.net/v1/repositories/1/content/1

Filter contents:

    curl https://api.coucal.net/v1/repositories/1/content?q=concept%3Dmeeting+dtstart%gt;startOfWeek()+dtstart%lt;endOfWeek()


### Channel

A Channel is a bidirectional asynchronous communication channel used for publishing content to subscribers, as well
as populating content from external sources.

### Subscription

A Subscription defines the consumers of published content. A Subscription may refer to individual content or an
entire Channel.

### Workspace

A Workspace represents an isolated user context for managing content. It is an aggregation of Repository and Channel,
and controls configuration and access to these constructs.

### User

A User represents an individual with access to the application for creating and managing content.

### Team

A Team is a grouping of one or more users that may be used to manage content and subscriptions collectively. The
benefit of a Team is shared access to Workspace content and subscriptions.


## Features

Coucou uses integrations with third-party platforms to store calendar and resource data. These Calendar
and Card Stores allow you to integrate Coucou easily with your existing calendaring and resource management
solutions.

### Calendar Connectors

By default, Coucou will use local file-based storage for calendar data, however the following connectors are
also supported:

* CalDAV compliant calendar servers. This includes popular tools like Google Calendar, Baikal, Radicale, etc.
* Relational Databases. Using the ical4j-connector schema you can manage data with high availabity and consistency.
* AWS DynamoDB. This popular NoSQL option provides a good option for cheap and fast storage.
* MongoDB. A popular document-oriented NoSQL solution.

### Card Connectors

As with Calendar Connectors, by default resource cards are managed via file-based local storage. Additional connectors
are similar to Calendar Connector support, with the following exceptions:

* CardDAV compliant servers. This is a counterpart to the CalDAV standard for managing resource card data.
* LDAP. A common standard for managing resources.

### Asset Stores

The management of assets is used to control access and enforce lifecycle policies of documents and artefacts
associated with calendar and resource data. As with the other stores, by default assets are stored on the local
filesystem, but other store types are also supported:

* AWS S3. Highly durable and inexpensive Cloud storage (includes a range of third-party providers that support the S3 API).
* Microsoft Sharepoint. A de-facto standard for office file sharing.
* Google Drive.
* Box.


### External Integrations

In order to share calendaring and resource management data, Coucou supports configurable integrations such as
email and messaging solutions. By default, data is shared via a local queue for testing purposes, but you can
also configure integrations with the following endpoint types:

* Email. Provide SMTP configuration to send data to relevant recipients.
* AWS Messaging. Cloud-native options such as AWS SQS, SNS and Event Bridge are supported.
* HTTP endpoints. You can integrate with custom APIs by providing an applicable webhook URL.
* Zapier. Integration with 1000s of applications.


### Collections

Collections are used to group calendar and resource data within a data store. For example, you may use a
different collection per project or team, in order to isolate the management of activities across the groups.

### Templates

Templates define the structure and default values for new calendar and resource data. Coucou includes a standard
set of templates, but you can also define new template instances that are stored in collections alongside
calendaring and resource data.

### Queries

Queries provide the ability to filter calendar and resource data using complex filter expressions to extract
relevant information to your collaborative activities.


## Command Line API

The command-line tool is designed around the core concepts of data stores, collections, templates and filters.

The simplest command that will display a list of all enabled connectors is as follows:

    $ coucal list
    /local
    ...


Note that if you don't specify a collection name the default store collection will be used. To query different
collections you can specify its virtual path as follows:

    $ coucal list /local
    /calendars
    /cards
    /attachments
    /sandbox
    ...


### Schedule creation

To create a new event in the default collection you can do the following:

    $ coucal new meeting -summary='Daily team check-in' -date=20230325 -time=1600 \
        -duration=30m -freq=daily -byday=mo,tu,we,th,fr \
        -attendees=fred@example.com,sally@example.com,bob@example.com
    Event created: 1234-abcd-9876-fa34

This may be simplified further with the use of event templates and resource groups. First create the template:

    $ coucal new meeting -duration=30m -freq=daily -byday=mo,tu,we,th,fr \
        -save-template=half-hour-meeting-on-weekdays
    Template created: 1234-abcd-9876-fa30

and then you can use this template:

    $ coucou new meeting -summary='Daily team check-in' -date=20230325 -time=1600 \
        -use-template=half-hour-meeting-on-weekdays \
        -resource-group=a-team
    Event created: 1234-abcd-9876-fa35

You can retrieve existing data in various formats as follows:

    $ coucou schedule get -uid=1234-abcd-9876-fa35 -format=text
    Summary: Daily team check-in
    Duration: 30 minutes
    Every: Weekday at 4pm
    Starting: March 25, 2023
    Confirmed Attendees: sally@example.com
    Unconfirmed Attendees: bob@example.com
    Apologies: fred@example.com

Note that if you don't specify a UID all data in the collection will be returned.

### Schedule integration

When a new event is created we need to notify attendees using a configured external integration mechanism
such as email. Assuming we have configured GMail as our integration we can send an event like this:

    $ coucou schedule send -uid=1234-abcd-9876-fa35 -integration=gmail
    Message sent.

Note that you can also use the `-auto-send` option when creating an event to automatically send it.

To update our event with responses from attendees we use the `receive` sub-command to scan integration endpoint
for any updates:

    $ coucou schedule receive -uid=1234-abcd-9876-fa35 -integration=gmail -auto-expunge
    bob@example.com has accepted.

Note the `-auto-expunge` option will automatically delete the received email message after processing.


### Schedule queries

Filter queries allow you to extract key information from collections, such as all unconfirmed attendance:

    $ coucou schedule get -match='attendee[rsvp=tentative]' -format=json \
        | jq '.attendee[] | select(.rsvp=tentative)'

Note how we still need to use a tool like JQ to filter the result as the match expression is used for
result matching, and does not affect the output of those results.

Again, we can simply this using saved queries:

    $ coucou schedule get -query=unconfirmed-attendance -format=json \
        | jq '.attendee[] | select(.rsvp=tentative)'


### Workflow Creation

Group workflows may be defined in a similar way to schedules. Note that a single collection can store events, tasks and
other calendar data. To retrieve tasks from a collection use the following:

    $ coucou tracker list
    ...

And to create a new task you can use something like this:

    $ coucou tracker task -summary='Weekly timesheets' -date=20230325 -time=1600 \
        -template=weekly-on-fridays \
        -resource-group=a-team
    Task created: 1234-abcd-9876-fa37

You can use queries to get information about incomplete tasks:

    $ coucou tracker get -query=incomplete-status -format=json \
        | jq '.attendee[] | select(.status=!completed)'


### Availability Management

Team rosters and resource availability can be managed via the availability sub-command:

    $ coucou availability list
    ...

This can be used to block certain times of the day, as follows:

    $ coucou availability block -summary='Lunch' -date=20230325 -time=1230 \
        -template=half-hour-on-weekdays \
        -resource-group=a-team
    Availability created: 1234-abcd-9876-fa38

### Journal Creation

Sharing documents and other assets can be coordinated via the journal sub-command:

    $ coucou journal list
    ...

Attachments are automatically stored in a configured asset store, ensuring lifecycle policies are adhered to:

    $ coucou journal publish -attach=@team-charter.pdf \
        -resource-group=a-team -status=draft -expires=30d
    Journal created: 1234-abcd-9876-fa39


### Entity Management

Resources and resource groups are managed via the entity sub-command:

    $ coucou entity list
    ...

TBD.

### Connectors

Using alternative backends can be managed via the connectors sub-command:

    $ coucou connectors list
    ...

TBD.

### Collections

Creating and maintaining collections in the configured connector(s) is done via the collections sub-command:

    $ coucou collections list
    ...

TBD.

### Alert Templates

You can provide


## REST-ful API

TBD.

