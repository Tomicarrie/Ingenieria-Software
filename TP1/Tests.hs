import Palet
import Stack
import Route
import Truck
import Control.Exception
import System.IO.Unsafe

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


rutaCorta = newR ["roma"]
rutaLarga = newR ["roma", "paris", "mdq", "berna"]
rutaVacia = newR []

paletRoma10 = newP "roma" 10
paletRoma5 = newP "roma" 5
paletParis5 = newP "paris" 5
paletMdq2 = newP "mdq" 2
paletLondres0 = newP "londres" 0 
paletLondresNeg1 = newP "londres" (-1)
paletBerlin7 = newP "Berlin" 7

stack5 = newS 5
stack0 =  newS 0
stackNeg1 = newS (-1)
stack10 =  newS 10

truckGrande = (newT 3 5 (rutaLarga))

truckVacio = (newT 0 5 (rutaLarga))

-- Test de la función newP en Palet
testNewP :: Bool
testNewP = 
  not (testF paletRoma10) &&    
  not (testF paletParis5) &&    
  testF paletLondres0 &&        
  testF paletLondresNeg1        

-- Test de la función destinationP en Palet
testDestinationP :: Bool
testDestinationP = 
  destinationP paletRoma10 == "roma" && 
  destinationP paletParis5 == "paris"

-- Test de la función netP en Palet
testNetP :: Bool
testNetP = 
  netP paletRoma10 == 10 && 
  netP paletParis5 == 5

-- Test de la función newR en Route
testNewR :: Bool
testNewR =
  not (testF (rutaLarga)) && 
  (testF (rutaVacia)) 

-- Test de la función inOrderR en Route
testInOrderR :: Bool
testInOrderR =
  inOrderR rutaCorta "roma" "paris" == True && 
  inOrderR rutaCorta "paris" "roma" == False &&
  inOrderR rutaLarga "roma" "mdq" == True &&
  inOrderR rutaLarga "berna" "roma" == False &&
  inOrderR rutaLarga "seul" "mdq" == False && -- seul no esta en la ruta
  inOrderR rutaLarga "mdq" "seul" == True 


-- Test de la función inRouteR en Route
testInRouteR :: Bool
testInRouteR =
  inRouteR rutaLarga "roma" == True && 
  inRouteR rutaLarga "londres" == False

-- Test de la función newS en Stack
testNewS :: Bool
testNewS =
  not (testF stack5) &&       
  testF (stack0) &&
  testF (stackNeg1)             

-- Test de la función freeCellsS en Stack
testFreeCellsS :: Bool
testFreeCellsS =
  freeCellsS stack5 == 5 &&
  freeCellsS (stackS stack5 paletRoma5) == 4


-- Test de la función stackS en Stack
testStackS :: Bool
testStackS =
  let stack = stackS stack10 paletRoma5
  in freeCellsS stack == 9 && netS stack == 5

-- Test de la función holdsS en Stack
testHoldsS :: Bool
testHoldsS =
  holdsS stack10 paletRoma5 rutaLarga == True &&
  holdsS stack10 paletBerlin7 (rutaLarga) == False

-- Test de la función popS en Stack
testPopS :: Bool
testPopS = 
  freeCellsS (popS (stackS stack5 paletRoma10) "roma") == 5 
  && netS (popS (stackS stack10 paletRoma5) "roma") == 0


-- Test de la función newT en Truck
testNewT :: Bool
testNewT =
  not (testF truckGrande) &&    
  (testF truckVacio)           

-- Test de la función freeCellsT en Truck
testFreeCellsT :: Bool
testFreeCellsT =
  freeCellsT truckGrande == 15

-- Test de la función loadT en Truck
testLoadT :: Bool
testLoadT =
  let truck = loadT truckGrande paletRoma5
  in freeCellsT truck == 14 && netT truck == 5

-- Test de la función unloadT en Truck
testUnloadT :: Bool
testUnloadT =
  let truck = unloadT truckGrande "roma"
  in freeCellsT truck == 15 && netT truck == 0

-- Test de la función netT en Truck
testNetT :: Bool
testNetT =
  netT truckGrande == 0 &&
  netT (loadT truckGrande paletRoma5) == 5

-- Lista con todos los tests
t :: [Bool]
t = [testNewP, testDestinationP, testNetP, testNewR, testInOrderR, testInRouteR, 
     testNewS, testFreeCellsS, testStackS, testHoldsS, 
     testPopS, testNewT, testFreeCellsT, testLoadT, testUnloadT, testNetT]

main = print t