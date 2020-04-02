# Örder self-checker
Örder is not only an awesome order management application, it is also a great self-evaluation, done by our best junior java developers.
The goal is not to "get it over with", the goal is to learn & grow:
 - Do I grasp the concepts?
 - Which topics do I get? Where can I challenge myself to go the extra mile?
 - Where do I struggle? Which topics should I work on?
 - Do codereviews on your own code. Make it work + improve and refine it. (for inspiration, look at https://switchfully.slack.com/files/UK99TQ0G6/F0100872XJA/codereview.pdf)
## Which choices did you make in your project
As a developer you have to make choices constantly.
Choices about time efficiency and quality, choices about your architecture, choices about functionality.
Which choices did you make? (List your choices below, remove what is not relevant)
 - What is your focus when working on Örder?
 > Make sure the must-have stories are implemented properly (= with tests, restful, etc.)
>Then try to get security working
>Then add nice-to-have stories
 - Do you have a single-module maven project? Or a multi-module maven project?
 > Single-module: once the functionality is done, I'll split it up into multiple modules.
 - Are you working test-first?
 > Yes.
 - Do you have security enabled?
 > No.
>(I haven't figured out how to give non-registered users access to a URL, so I disabled all security for practical reasons.)
 - Do you have logging enabled?
 > Partially.
 - What happens if the firstname contains numbers? Is this allowed?
 > At the moment, yes.
 - In which currency are the prices?
 > At the moment, prices are just numbers, not a separate class. (Because of POST issues, see below)
 - Does the phonenumber include the country code?
 > At the moment, phone numbers are just numbers, not a separate class. (Because of POST issues, see below)
 - Did you go for a similar architecture as digibooky?
 > More or less.
 - Is your application deployed in the cloud? (Heroku?)
 > No.
 - ....
## Progress Story 0 (project setup & technical requirements)
 - [x] Be awesome
 - [x] Setup Github
 - [x] Setup Jenkins
 - [x] Basic setup / skeleton for Spring Boot & Maven
 - [x] I have my first REST Controller ready
 - [x] I commit/push my code on a regular basis
 - [x] Swagger is enabled
### Comments:
## Story 1
 - [x] The functional requirements are implemented
 - [x] The requirements are tested with Postman
 - [x] The requirements are unit tested
 - [x] The requirements are integration tested
 - [x] Proper logging is setup
 - [ ] I did a "codereview" of my own code before declaring it "done".
 - [x] I commit/push my code when this story was done
### Comments:
## Story 2
 - [x] The functional requirements are implemented
 - [x] The requirements are tested with Postman
 - [x] The requirements are unit tested
 - [x] The requirements are integration tested
 - [x] Proper logging is setup
 - [ ] I did a "codereview" of my own code before declaring it "done".
 - [x] I commit/push my code when this story was done
### Comments:
## Story 3
 - [x] The functional requirements are implemented
 - [x] The requirements are tested with Postman
 - [x] The requirements are unit tested
 - [ ] The requirements are integration tested
 - [ ] Proper logging is setup
 - [ ] I did a "codereview" of my own code before declaring it "done".
 - [x] I commit/push my code when this story was done
### Comments: 
## Story 7
 - [x] The functional requirements are implemented
 - [x] The requirements are tested with Postman
 - [x] The requirements are unit tested
 - [x] The requirements are integration tested
 - [] Proper logging is setup
 - [ ] I did a "codereview" of my own code before declaring it "done".
 - [x] I commit/push my code when this story was done
### Comments: 
## Story 8
 - [ ] The functional requirements are implemented
 - [ ] The requirements are tested with Postman
 - [ ] The requirements are unit tested
 - [ ] The requirements are integration tested
 - [ ] Proper logging is setup
 - [ ] I did a "codereview" of my own code before declaring it "done".
 - [ ] I commit/push my code when this story was done
### Comments: 
## Nice-To-Have stories
 - [ ] Story 4
 - [ ] Story 5
 - [ ] Story 6
 - [ ] Story 9
 - [partially done] Story 10
### Comments:
The main issue is debugging POST messages. If something goes wrong with an incoming POST request, the system gives
strange and completely unhelpful error messages. It seems to be related to composite objects (objects within objects)?
I'm trying to figure that out.