//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.9
//
// <auto-generated>
//
// Generated from file `smart-home.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHome;

public interface EspressoCoffeeMachine extends CoffeeMachine
{
    BrewResult prepareLatte(com.zeroc.Ice.Current current);

    BrewResult prepareEspresso(com.zeroc.Ice.Current current);

    double getRemainingMilkPercentage(com.zeroc.Ice.Current current);

    void refillMilk(com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::SmartHome::CoffeeMachine",
        "::SmartHome::Device",
        "::SmartHome::EspressoCoffeeMachine"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::SmartHome::EspressoCoffeeMachine";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_prepareLatte(EspressoCoffeeMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        BrewResult ret = obj.prepareLatte(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        BrewResult.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_prepareEspresso(EspressoCoffeeMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        BrewResult ret = obj.prepareEspresso(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        BrewResult.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getRemainingMilkPercentage(EspressoCoffeeMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        double ret = obj.getRemainingMilkPercentage(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeDouble(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_refillMilk(EspressoCoffeeMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        obj.refillMilk(current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "emptyGroundsContainer",
        "getGroundsContainerFillPercentage",
        "getRemainingBeansPercentage",
        "getRemainingMilkPercentage",
        "getRemainingWaterPercentage",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "prepareAmericano",
        "prepareEspresso",
        "prepareLatte",
        "refillBeans",
        "refillMilk",
        "refillWater",
        "showHelp"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return CoffeeMachine._iceD_emptyGroundsContainer(this, in, current);
            }
            case 1:
            {
                return CoffeeMachine._iceD_getGroundsContainerFillPercentage(this, in, current);
            }
            case 2:
            {
                return CoffeeMachine._iceD_getRemainingBeansPercentage(this, in, current);
            }
            case 3:
            {
                return _iceD_getRemainingMilkPercentage(this, in, current);
            }
            case 4:
            {
                return CoffeeMachine._iceD_getRemainingWaterPercentage(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 8:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 9:
            {
                return CoffeeMachine._iceD_prepareAmericano(this, in, current);
            }
            case 10:
            {
                return _iceD_prepareEspresso(this, in, current);
            }
            case 11:
            {
                return _iceD_prepareLatte(this, in, current);
            }
            case 12:
            {
                return CoffeeMachine._iceD_refillBeans(this, in, current);
            }
            case 13:
            {
                return _iceD_refillMilk(this, in, current);
            }
            case 14:
            {
                return CoffeeMachine._iceD_refillWater(this, in, current);
            }
            case 15:
            {
                return Device._iceD_showHelp(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}