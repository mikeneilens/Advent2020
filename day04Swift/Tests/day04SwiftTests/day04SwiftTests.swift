import XCTest
@testable import day04Swift

final class day04SwiftTests: XCTestCase {
    let sampleData = """
        ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
        byr:1937 iyr:2017 cid:147 hgt:183cm

        iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
        hcl:#cfa07d byr:1929

        hcl:#ae17e1 iyr:2013
        eyr:2024
        ecl:brn pid:760753108 byr:1931
        hgt:179cm

        hcl:#cfa07d eyr:2025 pid:166559648
        iyr:2011 ecl:brn hgt:59in
        """
    
    func testSplitingDataIntoSeparatePassportsCreates4PotentialPassports() {

            let expectedPassport0 = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm
            """
            let expectedPassport3 = """
            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in
            """
            XCTAssertEqual(4, sampleData.toPassports().count)
            XCTAssertEqual(expectedPassport0, sampleData.toPassports()[0])
            XCTAssertEqual(expectedPassport3, sampleData.toPassports()[3])

        }
    
    func testAPairIsCreatedCorrectyWhenAStringContainsKeyAndValueSeparatedByAColon(){
        XCTAssertEqual("key123", "key123:abcd".toKeyValuePair().0)
        XCTAssertEqual("abcd", "key123:abcd".toKeyValuePair().1)
        }
    
    func testAMapContainingCorrectKeyValeusIsCreatedWithAPassportWhenCredentialsAreSeparatedBySpaces(){
            let passport = "iyr:2013 ecl:amb cid:350 eyr:2023"
            let credentials = passport.toCredentials()
            XCTAssertEqual(4,credentials.count)
            XCTAssertEqual("2013", credentials["iyr"])
            XCTAssertEqual("amb", credentials["ecl"])
            XCTAssertEqual("350", credentials["cid"])
            XCTAssertEqual("2023", credentials["eyr"])
        }
    
    func testCredentialsAreAValidPassportIfTheyContainAllOfTheKeysNeeded() {
        let credentials = ["ecl":"gry","pid":"860033327", "eyr":"2020", "hcl":"#fffffd","byr":"1937", "iyr":"2017", "cid":"147", "hgt":"183cm"]
        XCTAssertTrue(credentials.passportFieldsArePresent())
    }
    func testCredentialsAreAValidPassportIfTheyDoNotContainAllOfTheKeysNeeded() {
        let credentials = ["ecl":"gry","pid":"860033327", "eyr":"2020", "hcl":"#fffffd","byr":"1937", "iyr":"2017", "hgt":"183cm"]
        XCTAssertFalse(credentials.passportFieldsArePresent())
    }
    func testCredentialsAreValidNorthPoleCredentialsIfTheyContainAllOfThePassportCredentialsExceptCid() {
        let credentials = ["ecl":"gry","pid":"860033327", "eyr":"2020", "hcl":"#fffffd","byr":"1937", "iyr":"2017", "hgt":"183cm"]
        XCTAssertTrue(credentials.northPoleCredentialsFieldsArePresent())
    }
    func testCredentialsAreNotValidNorthPoleCredentialsIfTheyDoNotContainAllOfThePassportCredentialsExceptCid() {
        let credentials = ["ecl":"gry","pid":"860033327", "eyr":"2020", "byr":"1937", "iyr":"2017", "hgt":"183cm"]
        XCTAssertFalse(credentials.northPoleCredentialsFieldsArePresent())
    }
    
    func testSampleDataShouldContain2ValidPassports() {
        XCTAssertEqual(2, sampleData.passportsContainingRequiredFields().count)

        let firstPassportCredentials = sampleData.toPassports()[0].toCredentials()
        let thirdPassportCredentials = sampleData.toPassports()[2].toCredentials()

        XCTAssertEqual(firstPassportCredentials, sampleData.passportsContainingRequiredFields()[0])
        XCTAssertEqual(thirdPassportCredentials, sampleData.passportsContainingRequiredFields()[1])
    }
    
    func testPartOne() {
        XCTAssertEqual(196, dayFourData.passportsContainingRequiredFields().count)
    }
    
}
