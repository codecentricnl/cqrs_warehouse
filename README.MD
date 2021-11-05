# CQRS Warehouse

Hey there! 🙋‍

Thank you for joining our workshop from event storming to eventsourcing.
I hope that our brief introduction to the Axon Framework was useful for you.

If you are reading this, I'm assuming that you are eager to get your hands dirty
and ready for some coding.

As you may, or may not have seen, we have already implemented the unit tests for this application.
It's up to you to make the unit tests succeed!

###First steps
We have already setup the application for you. So you don't have to worry about it.
Today we are only focussing on the controllers, domain logic and projections.

We're starting off with an easy one, and that is also the first thing that we have to do in the application:

**Creating an article**

_Controller_

To create an article we are first going to add the logic to the `ArticleController`.
If you look at the `ArticleControllerTest` we expect that we call the `createArticle()` with a command.
As I explained earlier, a command has the intention to change state. In this case it has the intention to create an Aggregate.

If you look at the `CreateArticleCommand` which we expect in the test, you can see it's a normal java object. There is currently only one thing missing...

A command has to provide the information to the system, so it knows on which Aggregate the command has to be applied.
This is done via the annotation: `@TargetAggregateIdentifier`. This annotation tells Axon to apply the command on the aggregate
with that identifier.

When we go back to the test and look at the verification steps, we can see that we need to publish this command to the commandGateway.

**You should now have enough information to fix your first test.**

Let's continue once you have fixed this test!

>If you need help at any time, just ask me or my colleagues..

_Aggregate_

Now we've got our controller logic setup for creating an article, we are going to continue with the aggregate.
The aggregate is an entity that is always kept in a consistent state. 


An aggregate is nothing more than a normal Java class. For Axon to identify this class as an Aggregate, we should annotate it with:
`@Aggregate`.

This aggregate should also have an identifier which is annotated by: `@AggregateIdentifier`. 

 
If you look at the `ArticleAggregateTest` you can see that we are using an AggregateTestFixture.
This fixture enables us to test our Aggregate. It offers a Given-When-Then syntax which we can use to test
what happens when a command is handled in our aggregate.

Since we are creating the aggregate in the first test, we are using `givenNoPriorActivity`. This indicates that no events have been handled in the aggregate.

Let's create our aggregate by writing a constructor for this aggregate which has a `CreateArticleCommand` as a parameter.
The constructor should be annotated with `@CommandHandler` and the body of our constructor will only apply a new `ArticleCreatedEvent` on the 
`AggregateLifecycle`.


If you have done it correctly, your code should look like this 👇

```java
 @CommandHandler
    public ArticleAggregate(CreateArticleCommand command) {
        AggregateLifecycle.apply(new ArticleCreatedEvent(command.getId(), command.getName()));
    }
```

Since the aggregate is an entity with a consistent state, we are going to change that state based on the applied events on the `AggregateLifecycle`.
This is done through a method which is annotated by `@EventSourcingHandler`. The parameter of the method indicates which event it will handle.
In our case it should be `ArticleCreatedEvent`.

Set the state of the aggregate in the body of the handler.

If you have done it correctly, your code should look like this 👇

```java
    @EventSourcingHandler
    private void on(ArticleCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.containers = new HashMap<>();
    }
```

Try and run your test for creating the article aggregate. It should work now!

> At this time we are able to create an article through a REST call, an aggregate is created and the state of that aggregate
> is set. To finish up this cycle we are going to save the article to our database!

_Projection_

A projection is a view model of your data. Based on the events which happen in your system
we update the projection. So just as we did in the `ArticleAggregate` we are going to handle the `ArticleCreatedEvent`
Although we annotated the EventHandler in the aggregate with `@EventSourcingHandler` we are going to handle the events in the projections
by annotating the methods with: `@EventHandler`. The difference between these two annotations is that when the event is handled
by the `EventSourcingHandler` the event is really published to the EventBus, which is then caught by the `EventHandlers`. Ergo, the projections.

Since we start by looking at the test, we can see that the logic is quite straightforward. We only need to
save an ArticleDTO to the `ArticleRepository`.  

Your code should look like this 👇
```java
    @EventHandler
    public void on(ArticleCreatedEvent event) {
        articleRepository.save(new ArticleDTO(event.getId().toString(), event.getName()));
    }
```

> Now we actually saved the article to our mongo database. And you might wonder, how are we going to retrieve those articles?

This is done by a `@QueryHandler`. Query handlers act on incoming query messages. 
These query messages are sent through the `QueryGateway`. In our application this is done in the controller.


> You are now ready to continue fixing the tests. My advice is to look at the event storming result and work your 
> way through the application in the same order as you read the event storming result from left to right. Your next objective
> would be to fix `CreateShipmentCommand`. Start with the controller, then domain and then projection.
>
>
>When continuiing fixing tests you may come across a few things which aren't quite straightforward. 
>I'll try to explain them below

##### Container within the article aggregate
In my presentation I mentioned that the aggregate is a combination of the AggregateRoot and AggregateMembers.
In our application `Article` is the Aggregate Root, and `Container` is it's Aggregate Member.

Aggregate Members have their own Command and Event Handlers and therefore also contribute to the consistent state of the 
Aggregate. 

Aggregate Members are linked via an annotation: `@AggregateMember` with an
`eventForwardingMode` of ForwardMatchingInstances.class

Your code should look like this 👇
     
```java
    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private Map<UUID, Container> containers;
```     

Your Container.java class should have a `@EntityId` specifying the identifying field of an Entity/Member.
This is required to be able to route a command or event to the correct aggregate member.

##### Saga
A Saga is a special type of Event Listener: one that manages a business transaction. 
Some transactions could be running for days or even weeks, while others are completed within a few milliseconds. 
In Axon, each instance of a Saga is responsible for managing a single business transaction. 
That means a Saga maintains state necessary to manage that transaction, continuing it or taking compensating actions to roll back any actions already taken.

In our case that transaction is the functionality of creating a customer-request and that the shipment will be handled.
This means:
1. Creating a shipment
2. Claiming containers
3. (Optional) Unclaim containers when we don't have enough stock
4. Prepare shipment to be loaded into truck
5. Depart shipment when all is loaded.

The state which is necessary for this business transaction is kept in the saga.

A saga also introduces a new kind of event handler, a `@SagaEventHandler` which
handles all the relevant events. The event that creates the saga, in our case `ShipmentInitialisedEvent` will also have an additional
annotation: `@StartSaga`. This annotation will create a new saga and invoke its event handler method when a matching event is published.

How does a Saga know on which events to trigger? 
This is done by the `associationProperty` parameter on `@SagaEventHandler`. In our case it should be:

```java
@SagaEventHandler(associationProperty = "shipmentId")
```

This means that all the events with the same shipmentId will be mapped to the correct Saga.

Since the saga looks after a business transaction, it means that we have a start, but also an end.

When an event is applied to the EventBus and picked up by the Saga it's possible that the Saga should be ended.
This is done with the annotation: `@EndSaga`


>This it, all the information is there to get this application working and all the tests should succeed.
> If you are not quite sure, please check out the master branch where you can see the implementation.


###Thank you! And I hope you have learned something new today!










