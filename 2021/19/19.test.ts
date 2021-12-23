import { resolve } from 'path';
import { readFile } from 'fs/promises';
import { doJigsaw, fitPiece, parseInput, partOne, partTwo } from './19';

const testInput = "--- scanner 0 ---\n404,-588,-901\n528,-643,409\n-838,591,734\n390,-675,-793\n-537,-823,-458\n-485,-357,347\n-345,-311,381\n-661,-816,-575\n-876,649,763\n-618,-824,-621\n553,345,-567\n474,580,667\n-447,-329,318\n-584,868,-557\n544,-627,-890\n564,392,-477\n455,729,728\n-892,524,684\n-689,845,-530\n423,-701,434\n7,-33,-71\n630,319,-379\n443,580,662\n-789,900,-551\n459,-707,401\n\n--- scanner 1 ---\n686,422,578\n605,423,415\n515,917,-361\n-336,658,858\n95,138,22\n-476,619,847\n-340,-569,-846\n567,-361,727\n-460,603,-452\n669,-402,600\n729,430,532\n-500,-761,534\n-322,571,750\n-466,-666,-811\n-429,-592,574\n-355,545,-477\n703,-491,-529\n-328,-685,520\n413,935,-424\n-391,539,-444\n586,-435,557\n-364,-763,-893\n807,-499,-711\n755,-354,-619\n553,889,-390\n\n--- scanner 2 ---\n649,640,665\n682,-795,504\n-784,533,-524\n-644,584,-595\n-588,-843,648\n-30,6,44\n-674,560,763\n500,723,-460\n609,671,-379\n-555,-800,653\n-675,-892,-343\n697,-426,-610\n578,704,681\n493,664,-388\n-671,-858,530\n-667,343,800\n571,-461,-707\n-138,-166,112\n-889,563,-600\n646,-828,498\n640,759,510\n-630,509,768\n-681,-892,-333\n673,-379,-804\n-742,-814,-386\n577,-820,562\n\n--- scanner 3 ---\n-589,542,597\n605,-692,669\n-500,565,-823\n-660,373,557\n-458,-679,-417\n-488,449,543\n-626,468,-788\n338,-750,-386\n528,-832,-391\n562,-778,733\n-938,-730,414\n543,643,-506\n-524,371,-870\n407,773,750\n-104,29,83\n378,-903,-323\n-778,-728,485\n426,699,580\n-438,-605,-362\n-469,-447,-387\n509,732,623\n647,635,-688\n-868,-804,481\n614,-800,639\n595,780,-596\n\n--- scanner 4 ---\n727,592,562\n-293,-554,779\n441,611,-461\n-714,465,-776\n-743,427,-804\n-660,-479,-426\n832,-632,460\n927,-485,-438\n408,393,-506\n466,436,-512\n110,16,151\n-258,-428,682\n-393,719,612\n-211,-452,876\n808,-476,-593\n-575,615,604\n-485,667,467\n-680,325,-822\n-627,-443,-432\n872,-547,-609\n833,512,582\n807,604,487\n839,-516,451\n891,-625,532\n-652,-548,-490\n30,-46,-14";
describe("part one", () => {
  it("parses input", () => {
    const inp = "--- scanner 0 ---\n0,2\n4,1\n3,3\n\n--- scanner 1 ---\n-1,-1\n-5,0\n-2,1"

    expect(parseInput(inp)).toEqual([{"beacons": [[0, 2], [4, 1], [3, 3]]}, {"beacons": [[-1, -1], [-5, 0], [-2, 1]]}]);
  })

  // it("simple test", () => {
  //   const inp = "--- scanner 0 ---\n0,2,0\n4,1,0\n3,3,0\n\n--- scanner 1 ---\n-1,-1,0\n-5,0,0\n-2,1,0";
  //   const parsed = parseInput(inp);
  //   expect(fitPiece(parsed.shift()!.beacons, parsed.shift()!.beacons)).toEqual(-1)
  // })

  it("test input", () => {
    const beacons = parseInput(testInput);
    const map = doJigsaw(beacons.shift()!.beacons, beacons);
    expect(map.sort((a, b) => a[0] - b[0])).toEqual([[-892,524,684],
[-876,649,763],
[-838,591,734],
[-789,900,-551],
[-739,-1745,668],
[-706,-3180,-659],
[-697,-3072,-689],
[-689,845,-530],
[-687,-1600,576],
[-661,-816,-575],
[-654,-3158,-753],
[-635,-1737,486],
[-631,-672,1502],
[-624,-1620,1868],
[-620,-3212,371],
[-618,-824,-621],
[-612,-1695,1788],
[-601,-1648,-643],
[-584,868,-557],
[-537,-823,-458],
[-532,-1715,1894],
[-518,-1681,-600],
[-499,-1607,-770],
[-485,-357,347],
[-470,-3283,303],
[-456,-621,1527],
[-447,-329,318],
[-430,-3130,366],
[-413,-627,1469],
[-345,-311,381],
[-36,-1284,1171],
[-27,-1108,-65],
[7,-33,-71],
[12,-2351,-103],
[26,-1119,1091],
[346,-2985,342],
[366,-3059,397],
[377,-2827,367],
[390,-675,-793],
[396,-1931,-563],
[404,-588,-901],
[408,-1815,803],
[423,-701,434],
[432,-2009,850],
[443,580,662],
[455,729,728],
[456,-540,1869],
[459,-707,401],
[465,-695,1988],
[474,580,667],
[496,-1584,1900],
[497,-1838,-617],
[527,-524,1933],
[528,-643,409],
[534,-1912,768],
[544,-627,-890],
[553,345,-567],
[564,392,-477],
[568,-2007,-577],
[605,-1665,1952],
[612,-1593,1893],
[630,319,-379],
[686,-3108,-505],
[776,-3184,-501],
[846,-3110,-434],
[1135,-1161,1235],
[1243,-1093,1063],
[1660,-552,429],
[1693,-557,386],
[1735,-437,1738],
[1749,-1800,1813],
[1772,-405,1572],
[1776,-675,371],
[1779,-442,1789],
[1780,-1548,337],
[1786,-1538,337],
[1847,-1591,415],
[1889,-1729,1762],
[1994,-1805,1792]])
    expect(partOne(testInput)).toEqual(79);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    //expect(partOne(puzzleInput)).toEqual(479); Takes 40 mins
  });
});

describe("part two", () => {
  it("test input", () => {
    expect(partTwo(testInput)).toEqual(3621);
  });

  it("puzzle input", async () => {
    const puzzleInput = (await readFile(resolve(__dirname, "input.txt"))).toString()
    //expect(partTwo(puzzleInput)).toEqual(13113); 50 mins
  });
});