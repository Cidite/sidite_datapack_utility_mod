package com.github.cidite.commands;


// /storage_operation value storage <스토리지 id> <스토리지 경로> <+|-|*|/|%|=> <value>
// /storage_operation from storage <스토리지 id> <스토리지 경로> <+|-|*|/|%|=> <스토리지 id> <스토리지 경로>  <-- 미완성. 곧 구현 예정임
// 스토리지를 쌩으로 계산하는 명령어

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.DataCommandObject;
import net.minecraft.command.StorageDataObject;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.nbt.AbstractNbtNumber;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtShort;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtLong;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.DataCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.DoubleFunction;

import static net.minecraft.server.command.DataCommand.getNbt;

public class StorageOperationCommand {
    private static final DynamicCommandExceptionType GET_INVALID_EXCEPTION = new DynamicCommandExceptionType((path) -> Text.stringifiedTranslatable("commands.data.get.invalid", path));
    private static final SimpleCommandExceptionType DIVISION_ZERO_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("arguments.operation.div0"));

    public static final List<Function<String, DataCommand.ObjectType>> OBJECT_TYPE_FACTORIES;
    public static final List<DataCommand.ObjectType> TARGET_OBJECT_TYPES;
    public static final List<DataCommand.ObjectType> SOURCE_OBJECT_TYPES;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("storage_operation").requires(source -> source.hasPermissionLevel(2));
        Iterator var2 = TARGET_OBJECT_TYPES.iterator();

        while(var2.hasNext()) {
            DataCommand.ObjectType objectType = (DataCommand.ObjectType)var2.next();
            literalArgumentBuilder
                    .then(objectType.addArgumentsToBuilder(CommandManager
                            .literal("value"),(builder) -> builder
                            .then(CommandManager
                                    .argument("path", NbtPathArgumentType.nbtPath())
                                    .then(CommandManager.literal("+")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .executes(context -> executeAdd(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value")
                                                    ))
                                            )
                                    )
                                    .then(CommandManager.literal("-")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeAdd(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value") * -1
                                                            ))
                                                    )
                                                    .executes(context -> executeAdd(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value") * -1
                                                    ))
                                            )
                                    )
                                    .then(CommandManager.literal("=")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeEqual(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .executes(context -> executeEqual(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value")
                                                    ))
                                            )
                                    )
                                    .then(CommandManager.literal("*")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeTimes(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .executes(context -> executeTimes(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value")
                                                    ))
                                            )
                                    )
                                    .then(CommandManager.literal("/")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeDiv(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .executes(context -> executeDiv(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value")
                                                    ))
                                            )
                                    )
                                    .then(CommandManager.literal("%")
                                            .then(CommandManager
                                                    .argument("value", DoubleArgumentType.doubleArg())
                                                    .then(CommandManager.literal("int")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtInt.of((int)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("double")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtDouble.of((Double)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("float")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtFloat.of((float)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("short")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtShort.of((short)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("long")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtLong.of((long)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .then(CommandManager.literal("byte")
                                                            .executes(context -> executeRem(context.getSource(),
                                                                    objectType.getObject(context),
                                                                    NbtPathArgumentType.getNbtPath(context, "path"),
                                                                    (result) -> NbtByte.of((byte)result),
                                                                    DoubleArgumentType.getDouble(context,"value")
                                                            ))
                                                    )
                                                    .executes(context -> executeRem(context.getSource(),
                                                            objectType.getObject(context),
                                                            NbtPathArgumentType.getNbtPath(context, "path"),
                                                            (result) -> NbtInt.of((int)result),
                                                            DoubleArgumentType.getDouble(context,"value")
                                                    ))
                                            )
                                    )
                                    .executes(context -> executeGet(context.getSource(),
                                            objectType.getObject(context),
                                            NbtPathArgumentType.getNbtPath(context, "path")
                                    ))
                            )
                    ));
        }

        dispatcher.register(literalArgumentBuilder);
    }

    private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        int i;
        if (nbtElement instanceof AbstractNbtNumber) {
            i = MathHelper.floor(((AbstractNbtNumber)nbtElement).doubleValue());
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return i;
    }

    private static int executeAdd(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, DoubleFunction<NbtElement> nbtSetter, double value) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        double i;
        if (nbtElement instanceof AbstractNbtNumber) {
            i = ((AbstractNbtNumber)nbtElement).doubleValue();
            NbtCompound nbtCompound = object.getNbt();
            path.put(nbtCompound, nbtSetter.apply(i + value));
            object.setNbt(nbtCompound);
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return (int) i;
    }

    private static int executeEqual(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, DoubleFunction<NbtElement> nbtSetter, double value) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        double i;
        if (nbtElement instanceof AbstractNbtNumber) {
            i = value;
            NbtCompound nbtCompound = object.getNbt();
            path.put(nbtCompound, nbtSetter.apply(value));
            object.setNbt(nbtCompound);
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return (int) i;
    }


    private static int executeTimes(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, DoubleFunction<NbtElement> nbtSetter, double value) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        double i;
        if (nbtElement instanceof AbstractNbtNumber) {
            i = ((AbstractNbtNumber)nbtElement).doubleValue();
            NbtCompound nbtCompound = object.getNbt();
            path.put(nbtCompound, nbtSetter.apply(i * value));
            object.setNbt(nbtCompound);
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return (int) i;
    }

    private static int executeDiv(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, DoubleFunction<NbtElement> nbtSetter, double value) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        double i;
        if (value == 0) {
            throw DIVISION_ZERO_EXCEPTION.create();
        }
        if (nbtElement instanceof AbstractNbtNumber) {
            i = ((AbstractNbtNumber)nbtElement).doubleValue();
            NbtCompound nbtCompound = object.getNbt();
            path.put(nbtCompound, nbtSetter.apply(i / value));
            object.setNbt(nbtCompound);
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return (int) i;
    }

    private static int executeRem(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, DoubleFunction<NbtElement> nbtSetter, double value) throws CommandSyntaxException {
        NbtElement nbtElement = getNbt(path, object);
        double i;
        if (value == 0) {
            throw DIVISION_ZERO_EXCEPTION.create();
        }
        if (nbtElement instanceof AbstractNbtNumber) {
            i = ((AbstractNbtNumber)nbtElement).doubleValue();
            NbtCompound nbtCompound = object.getNbt();
            path.put(nbtCompound, nbtSetter.apply(i % value));
            object.setNbt(nbtCompound);
        } else {
            throw GET_INVALID_EXCEPTION.create(path.toString());
        }
        source.sendFeedback(() -> object.feedbackQuery(nbtElement), false);
        return (int) i;
    }

    static {
        OBJECT_TYPE_FACTORIES = ImmutableList.of(StorageDataObject.TYPE_FACTORY);
        TARGET_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream().map((factory) -> {
            return factory.apply("target");
        }).collect(ImmutableList.toImmutableList());
        SOURCE_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream().map((factory) -> {
            return factory.apply("source");
        }).collect(ImmutableList.toImmutableList());
    }

}
