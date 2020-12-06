import XCTest
@testable import day06Swift

final class day06SwiftTests: XCTestCase {
    let sampleData = """
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
        """
    func testPartOneUsingSampleData() {
        XCTAssertEqual(11, sampleData.uniqueAnswers())
    }
    func testPartOne() {        
        XCTAssertEqual(7120, day06Data.uniqueAnswers())
    }
    func testPartTwoUsingSampleData() {
        XCTAssertEqual(6,sampleData.sumOfQuestionsAnsweredByEachGroup())
    }
    func testPartTwo() {
        XCTAssertEqual(3570, day06Data.sumOfQuestionsAnsweredByEachGroup())
    }
}
