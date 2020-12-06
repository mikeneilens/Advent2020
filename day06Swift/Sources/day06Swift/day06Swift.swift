
extension String {
    func parseIntoGroups() -> Array<String> { split(separator: "\n\n") }
    
    func uniqueAnswers() ->Int {
        parseIntoGroups()
        .map(answersForGroup)
        .map(uniqueAnswersInGroup)
        .reduce(0, +)
    }
    
    func sumOfQuestionsAnsweredByEachGroup() -> Int {
        parseIntoGroups()
        .map(answersForGroup)
        .map(questionsEveryOneAnswers)
        .map{ $0.count }
        .reduce(0,+)
    }
}

func answersForGroup(_ group:String) -> Array<String> { group.split(separator: "\n") }

func uniqueAnswersInGroup(_ answers:Array<String>) -> Int { Set(answers.joined()).count }

func questionsEveryOneAnswers(groupAnswers:Array<String>) -> Array<Character> {
    "abcdefghijklmnopqrstuvwxyz".filter{ groupAnswers.allPeopleInTheGroupAnswers($0) }
}

extension Array where Element == String {
    func allPeopleInTheGroupAnswers(_ a:Character) ->Bool {allSatisfy{$0.contains(a)}}
}


