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


testNewP :: Bool
testNewP = 
  not (testF paletRoma10) &&    
  not (testF paletParis5) &&    
  testF paletLondres0 &&        
  testF paletLondresNeg1        


testDestinationP :: Bool
testDestinationP = 
  destinationP paletRoma10 == "roma" && 
  destinationP paletParis5 == "paris"


testNetP :: Bool
testNetP = 
  netP paletRoma10 == 10 && 
  netP paletParis5 == 5


testNewR :: Bool
testNewR =
  not (testF (rutaLarga)) && 
  (testF (rutaVacia)) 


testInOrderR :: Bool
testInOrderR =
  inOrderR rutaCorta "roma" "paris" == True && 
  inOrderR rutaCorta "paris" "roma" == False &&
  inOrderR rutaLarga "roma" "mdq" == True &&
  inOrderR rutaLarga "berna" "roma" == False &&
  inOrderR rutaLarga "seul" "mdq" == False && -- seul no esta en la ruta
  inOrderR rutaLarga "mdq" "seul" == True 



testInRouteR :: Bool
testInRouteR =
  inRouteR rutaLarga "roma" == True && 
  inRouteR rutaLarga "londres" == False


testNewS :: Bool
testNewS =
  not (testF stack5) &&       
  testF (stack0) &&
  testF (stackNeg1)             


testFreeCellsS :: Bool
testFreeCellsS =
  freeCellsS stack5 == 5 &&
  freeCellsS (stackS stack5 paletRoma5) == 4



testStackS :: Bool
testStackS =
  freeCellsS stackAfter == 9 &&
  netS stackAfter == 5
  where
    stackAfter = stackS stack10 paletRoma5


testHoldsS :: Bool
testHoldsS =
  holdsS stack10 paletRoma5 rutaLarga == True &&
  holdsS stack10 paletBerlin7 (rutaLarga) == False


testPopS :: Bool
testPopS = 
  freeCellsS (popS (stackS stack5 paletRoma10) "roma") == 5 
  && netS (popS (stackS stack10 paletRoma5) "roma") == 0



testNewT :: Bool
testNewT =
  not (testF truckGrande) &&    
  (testF truckVacio)           


testFreeCellsT :: Bool
testFreeCellsT =
  freeCellsT truckGrande == 15


testLoadT :: Bool
testLoadT =
  freeCellsT truckAfterLoad == 14 &&
  netT truckAfterLoad == 5
  where
    truckAfterLoad = loadT truckGrande paletRoma5



testUnloadT :: Bool
testUnloadT =
  freeCellsT truckAfterUnload == 15 &&
  netT truckAfterUnload == 0
  where
    truckAfterUnload = unloadT truckGrande "roma"


testNetT :: Bool
testNetT =
  netT truckGrande == 0 &&
  netT (loadT truckGrande paletRoma5) == 5


t :: [Bool]
t = [testNewP, testDestinationP, testNetP, testNewR, testInOrderR, testInRouteR, 
     testNewS, testFreeCellsS, testStackS, testHoldsS, 
     testPopS, testNewT, testFreeCellsT, testLoadT, testUnloadT, testNetT]

main = print t