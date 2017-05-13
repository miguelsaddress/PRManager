Pull Request Manager
=====================

This project is not trying to invent something revolutionary, just to be an excuse to make us built something relatively useful together (so it keeps us motivated and focused) and learn together and from each other some technologies like Scala, akka, Lagom, Play, API handling...

# 1. General Idea
The idea is to have a basic pull request manager that, given a repository, can:


* A - Display the pull requests of that repo
* B - Order the PRs by date
* C - Filter the pull request by:
	* 1 - creator
	* 2 - reviewer
	* 3 - no reviewers assigned
	* 4 - status (open, closed, partially approved, approved, rejected)
		* 4.1 - Rejection reasons [See #5.C]
	* 5 - destination branch
	* 6 - source branch
	* 7 - priority (High | Normal) [Read #5]
	* 8 - tags

# 2. Member General Data
Also, the idea is to see a list of all the members of the repository and for each one of them, see the following data:

* A - image
* B - name
* C - username
* D - amount of PRs created
* E - amount of PRs currently reviewing
* F - amount of PRs already reviewed

# 3. Member Detailed Data
Also, given a member, we can see a list of:

* A - PRs created by him
* B - PRs currently reviewing
* C - PRs already reviewed

# 4. Member API iteraction
The application should allow us to take the following actions on behalf of a user

* A - retrieve all the needed information previously mentioned (name, image, PRs...)
* B - approve / unapprove


# 5. What application offers on top of the API
Things the application can do unrelated to managing the APIs


* A - Assign a Team to a user
* B - Add Tags to a PR
* C - Decline a PR: Meaning that the user disagrees with the PR, but the PR continous to be opened. Reasons must be given. One of the following plus a text.
	* Architecture Flaws
	* Not as agreed
	* Performance Issues
	* Security Issues
	* Other
D - If some markers are in the name of the PR, it should
	* [Prio: H]: High Priority
	* [PER|PREC|-xxx]: Builds a Jira ticket link to that ticket. How to build this link should be configurable in the project settings
	* [XT]: Cross Team. The PR must be approved by at least one member of each team

# 6. Constraints

- Server side and client side should be isolated
- For the Front-End: Redux must be used
- For the Back-End: Scala must be used
- Slick will be used (and therefore RDBMS such as MySql)
- Play Framework will be used
- Akka will be used when needed
- Must connect to Bitbucket and Github APIs
- Data update spread: When data gets updated in the database as a result of an action of one of the users, that data should be pushed to the rest of activeusers (web sockets) without reloading the page

# 7. Other features and Nice-to-have's

- Configure rejection reasons by project
- Configure title markers by project
- Integrate it wih Slack via bot
- Integrate it Telegram via bot


